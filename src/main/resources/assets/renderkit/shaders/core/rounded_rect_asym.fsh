#version 330
#moj_import <minecraft:dynamictransforms.glsl>

in vec2 vUv;
flat in vec2 vHalfSize;
flat in float vRadius;
in vec4 vColor;

out vec4 fragColor;

void main() {

    float r = vUv.y > 0.0 ? vRadius : 0.0;
    vec2 q = abs(vUv) - vHalfSize + r;
    float dist = length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - r;

    float aa = max(fwidth(dist), 1e-5);
    float alpha = clamp(0.5 - dist / aa, 0.0, 1.0);
    if (alpha <= 0.0) {
        discard;
    }
    fragColor = vec4(vColor.rgb, vColor.a * alpha) * ColorModulator;
}
