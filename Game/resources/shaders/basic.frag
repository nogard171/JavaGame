//uniform sampler2D myTexture;

uniform vec4 vertColor;

//out vec4 color;

void main()
{
	//color = vertColor;
	gl_FragColor = vertColor;
}