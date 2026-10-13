#version 330

#moj_import <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec2 vUv;
flat in vec2 vHalfExtent;
in vec4 vColor;

out vec4 fragColor;

void main() {
    float dist = length(max(abs(vUv) - vHalfExtent, 0.0)) - 1.0;

    float aa = max(fwidth(dist), 1e-5);
    float alpha = clamp(0.5 - dist / aa, 0.0, 1.0);

    if (alpha <= 0.0) {
        discard;
    }

    vec2 uv = gl_FragCoord.xy / vec2(textureSize(Sampler0, 0));
    vec3 blurred = texture(Sampler0, uv).rgb;

    fragColor = vec4(blurred * vColor.rgb, vColor.a * alpha) * ColorModulator;
}
