#version 330 core

uniform sampler2D myTexture;

out vec4 color;

void main()
{
    color = texture(myTexture, gl_TexCoord[0].st);
}