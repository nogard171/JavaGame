#version 330 core

uniform sampler2D myTexture;
uniform vec4 entityColor;

out vec4 color;

void main()
{
    color = texture(myTexture, gl_TexCoord[0].xy)*entityColor;
}