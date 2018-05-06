#version 330 core

uniform vec3 position;
uniform vec3 chunkPosition;

uniform vec2 view;

void main(void)
{
	
	mat4x4 pos=mat4x4(1.0);
	
    pos[3].x=position.x+view.x+chunkPosition.x;
    pos[3].y=position.y+view.y+chunkPosition.y;
    

	gl_TexCoord[0] = gl_MultiTexCoord0;
 	gl_Position = gl_ModelViewProjectionMatrix*pos*gl_Vertex;

}