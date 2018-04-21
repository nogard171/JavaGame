#version 330 core

uniform vec3 position;

uniform vec2 view;

void main(void)
{
	float x = (position.x * 32) - (position.z * 32);
	float y = (position.y * 32);
	float z = (position.z * 16) + (position.x * 16);
	
	mat4x4 pos=mat4x4(1.0);
	
    pos[3].x=x+view.x;
    pos[3].y=-z+y+view.y;
    //pos[3].z=z+y;
    

	gl_TexCoord[0] = gl_MultiTexCoord0;
 	gl_Position = gl_ModelViewProjectionMatrix*pos*gl_Vertex;

}