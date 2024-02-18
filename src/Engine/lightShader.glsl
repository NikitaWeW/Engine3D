#version 460 core
vec3 finalColor(vec3 lightColor, vec3 lightAmbientIntensity, vec3 pixelBaseColor, vec3 materialAmbientColor) {
    return vec3(lightColor*lightAmbientIntensity*pixelBaseColor*materialAmbientColor);
}
