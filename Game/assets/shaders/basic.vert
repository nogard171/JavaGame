#version 330 core

uniform vec3 position;

void main(void)
{
	mat4x4 pos=mat4x4(1.0);
    pos[3].x=position.x;
    pos[3].y=position.y;


	gl_TexCoord[0] = gl_MultiTexCoord0;
 	gl_Position = gl_ModelViewProjectionMatrix*pos*gl_Vertex;
 	//gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;
}