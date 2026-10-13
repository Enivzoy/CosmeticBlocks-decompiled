#version 330

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec2 vUv;
flat in vec2 vHalfSize;
flat in float vRadius;
in vec4 vColor;

out vec4 fragColor;

const vec2 FACE_ORIGIN = vec2(0.125, 0.125);
const vec2 FACE_SIZE = vec2(0.125, 0.125);

float roundedBoxDistance(vec2 p, vec2 halfSize, float radius) {
    vec2 inner = max(halfSize - radius, 0.0);
    return length(max(abs(p) - inner, 0.0)) - radius;
}

void main() {
    float dist = roundedBoxDistance(vUv, vHalfSize, vRadius);

    float aa = max(fwidth(dist), 1e-5);
    float mask = clamp(0.5 - dist / aa, 0.0, 1.0);

    if (mask <= 0.0) {
        discard;
    }

    vec2 quadUv = vUv / (vHalfSize * 2.0) + 0.5;
    vec4 texel = texture(Sampler0, FACE_ORIGIN + quadUv * FACE_SIZE);

    fragColor = vec4(texel.rgb * vColor.rgb, texel.a * vColor.a * mask) * ColorModulator;
}
