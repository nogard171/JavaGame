uniform sampler2D myTexture;

uniform vec4 vertColor;

//out vec4 color;

void main()
{
    gl_FragColor = texture2D(myTexture, gl_TexCoord[0].st)*vertColor;
    //gl_FragColor = vertColor;
}