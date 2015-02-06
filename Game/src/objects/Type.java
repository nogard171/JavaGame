package objects;

import java.awt.image.BufferedImage;

//this enum identify's types throughout the game
public enum Type {
	Player,
    Blank,
    Grass,
    Dirt,
    Sand,
    Stone,
    Water,
    Ice,
    Tree, 
    Bush;
	public static BufferedImage getTexture(BufferedImage texture, objects.Type type,int season)
	{
		return getTextureMain(texture,type,season);
	}
	public static BufferedImage getTexture(BufferedImage texture, objects.Type type)
	{
		return getTextureMain(texture,type,0);
	}
	public static BufferedImage getTextureMain(BufferedImage texture, objects.Type type,int season)
	{
		if(type==objects.Type.Player)
		{
			return texture.getSubimage((season*128)+8, 768, 64,64);
		}
		if(type==objects.Type.Tree)
		{
			return texture.getSubimage((season*128)+8, (768+8), 64,(64-8));
		}
		if(type==objects.Type.Bush)
		{
			return texture.getSubimage(season*128, 640, 64,128);
		}
		if(type==objects.Type.Grass)
		{
			return texture.getSubimage(64, 64, 64,64);
		}
		else
		{
			return null;
		}
	}
}