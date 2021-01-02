#version 110

uniform vec2 position;

varying vec4 vertColor;

void main(){
    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex*vec4(position.xy, 0.0, 1.0);
    vertColor = vec4(0.6, 0.3, 0.4, 1.0);
}