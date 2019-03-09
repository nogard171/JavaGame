uniform vec2 position;

uniform vec2 cameraPosition;

void main(void)
{
	mat4x4 pos=mat4x4(1.0);
    pos[3].x=position.x+cameraPosition.x;
    pos[3].y=position.y+cameraPosition.y;


 	gl_Position = gl_ModelViewProjectionMatrix*pos*gl_Vertex;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}