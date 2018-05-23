#version 330 core

uniform vec2 position;

uniform vec2 view;

void main(void)
{
	
	mat4x4 pos=mat4x4(1.0);
	
    pos[3].x=position.x+view.x;
    pos[3].y=position.y+view.y;
    

	gl_TexCoord[0] = gl_MultiTexCoord0;
 	gl_Position = gl_ModelViewProjectionMatrix*pos*gl_Vertex;

}