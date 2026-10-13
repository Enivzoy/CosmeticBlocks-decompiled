/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.io.ByteStreamsKt
 *  kotlin.io.CloseableKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lcom/cb/utils/ConfigBackup;", "", "<init>", "()V", "cbDir", "Ljava/io/File;", "backupDir", "timestampFormat", "Ljava/time/format/DateTimeFormatter;", "kotlin.jvm.PlatformType", "backup", "", "cb_client"})
@SourceDebugExtension(value={"SMAP\nConfigBackup.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigBackup.kt\ncom/cb/utils/ConfigBackup\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n1#2:45\n*E\n"})
public final class ConfigBackup {
    @NotNull
    public static final ConfigBackup INSTANCE = new ConfigBackup();
    @NotNull
    private static final File cbDir = new File("config/cb/cb");
    @NotNull
    private static final File backupDir = new File("config/cb/cb_backups");
    private static final DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private ConfigBackup() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public final String backup() {
        String string;
        if (!cbDir.exists()) {
            return "No CB configs found";
        }
        File[] fileArray = cbDir.listFiles(ConfigBackup::backup$lambda$0);
        if (fileArray == null) {
            return "No CB configs found";
        }
        File[] files = fileArray;
        if (files.length == 0) {
            return "No CB configs found";
        }
        try {
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }
            String timestamp = LocalDateTime.now().format(timestampFormat);
            File zipFile = new File(backupDir, "cb_backup_" + timestamp + ".zip");
            Closeable closeable = new ZipOutputStream(new FileOutputStream(zipFile));
            Throwable throwable = null;
            try {
                ZipOutputStream zos = (ZipOutputStream)closeable;
                boolean bl = false;
                int n = files.length;
                for (int i = 0; i < n; ++i) {
                    File file = files[i];
                    try {
                        zos.putNextEntry(new ZipEntry(file.getName()));
                        Intrinsics.checkNotNull((Object)file);
                        Closeable closeable2 = new FileInputStream(file);
                        Throwable throwable2 = null;
                        try {
                            FileInputStream it = (FileInputStream)closeable2;
                            boolean bl2 = false;
                            long l = ByteStreamsKt.copyTo$default((InputStream)it, (OutputStream)zos, (int)0, (int)2, null);
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            CloseableKt.closeFinally((Closeable)closeable2, (Throwable)throwable2);
                        }
                        zos.closeEntry();
                        continue;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
            }
            string = "Backup saved: cb_backup_" + timestamp + ".zip (" + files.length + " configs)";
        }
        catch (Exception e) {
            string = "Backup failed: " + e.getMessage();
        }
        return string;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static final boolean backup$lambda$0(File f) {
        if (!f.isFile()) return false;
        Intrinsics.checkNotNull((Object)f);
        if (!Intrinsics.areEqual((Object)FilesKt.getExtension((File)f), (Object)"json")) return false;
        return true;
    }
}


