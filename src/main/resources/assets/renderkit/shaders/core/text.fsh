#version 330
#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

void main() {

    float coverage = texture(Sampler0, texCoord0).r;
    if (coverage <= 0.0) {
        discard;
    }
    fragColor = vec4(vertexColor.rgb, vertexColor.a * coverage) * ColorModulator;
}
