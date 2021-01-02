#version 110

uniform vec2 position;

varying vec4 vertColor;

void main(){
	mat4x4 pos=mat4x4(1.0);
    pos[3].x=position.x;
    pos[3].y=position.y;
    
    
    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex*pos;
    vertColor = vec4(0.6, 0.3, 0.4, 1.0);
}