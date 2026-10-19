package me.enivzoy.cosmeticblocksreborn.utils

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft

object Scheduler {
   private var actions: MutableList<() -> Unit> = mutableListOf()
   private var nextTaskId = 0
   private val delayedTasks = LinkedHashMap<Int, DelayedTask>()
   private val repeatingTasks = LinkedHashMap<Int, RepeatingTask>()

   fun scheduleTickEnd(action: () -> Unit): Boolean = actions.add(action)

   fun schedule(delayTicks: Int, action: () -> Unit): Int {
      val id = nextTaskId++
      delayedTasks[id] = DelayedTask(delayTicks, action)
      return id
   }

   fun schedule(delayTicks: Long, action: () -> Unit): Int =
      this.schedule(delayTicks.toInt(), action)

   fun scheduleRepeating(intervalTicks: Long, action: () -> Unit): Int {
      val id = nextTaskId++
      repeatingTasks[id] = RepeatingTask(intervalTicks.toInt(), intervalTicks.toInt(), action)
      return id
   }

   fun cancel(taskId: Int) {
      delayedTasks.remove(taskId)
      repeatingTasks.remove(taskId)
   }

   fun register() {
      ClientTickEvents.END_CLIENT_TICK.register(::onEndClientTick)
   }

   private fun onEndClientTick(client: Minecraft) {
      for (action in actions) {
         action.invoke()
      }

      actions.clear()

      val expiredIds = mutableListOf<Int>()
      for ((id, task) in delayedTasks) {
         task.ticksRemaining -= 1
         if (task.ticksRemaining <= 0) {
            try {
               task.action.invoke()
            } catch (e: Exception) {
               println("CB: Scheduler delayed task error: ${e.message}")
            }

            expiredIds.add(id)
         }
      }

      for (id in expiredIds) {
         delayedTasks.remove(id)
      }

      val repeatingToRemove = mutableListOf<Int>()
      for ((id, task) in repeatingTasks) {
         task.ticksRemaining -= 1
         if (task.ticksRemaining <= 0) {
            try {
               task.action.invoke()
            } catch (e: Exception) {
               println("CB: Scheduler repeating task error: ${e.message}")
               repeatingToRemove.add(id)
            }

            task.ticksRemaining = task.interval
         }
      }

      for (id in repeatingToRemove) {
         repeatingTasks.remove(id)
      }
   }

   private class DelayedTask(var ticksRemaining: Int, val action: () -> Unit)

   private class RepeatingTask(val interval: Int, var ticksRemaining: Int, val action: () -> Unit)
}
