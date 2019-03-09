uniform sampler2D myTexture;

uniform vec4 vertColor;


void main()
{
    gl_FragColor = texture2D(myTexture, gl_TexCoord[0].st);//*vertColor;
}