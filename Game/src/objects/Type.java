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
    Bush,
    Rock;
	public static BufferedImage getTexture(BufferedImage texture, objects.Type type)
	{
		return getTextureMain(texture,type);
	}
	public static BufferedImage getTextureMain(BufferedImage texture, objects.Type type)
	{
		if(type==objects.Type.Tree)
		{
			return texture.getSubimage(0, (768+8), 64,(64-8));
		}
		if(type==objects.Type.Bush)
		{
			return texture.getSubimage(0, 640, 64,128);
		}
		if(type==objects.Type.Grass)
		{
			return texture.getSubimage(64, 64, 64,64);
		}
		if(type==objects.Type.Dirt)
		{
			return texture.getSubimage(384, 64, 64,64);
		}
		if(type==objects.Type.Rock)
		{
			return texture.getSubimage(0, 832, 64,64);
		}
		else
		{
			return null;
		}
	}
	public static Type parse(String string) {
		// TODO Auto-generated method stub
		if(string.toLowerCase().equals("tree"))
		{
			return Tree;
		}
		if(string.toLowerCase().equals("bush"))
		{
			return Bush;
		}
		if(string.toLowerCase().equals("grass"))
		{
			return Grass;
		}
		if(string.toLowerCase().equals("dirt"))
		{
			return Dirt;
		}
		if(string.toLowerCase().equals("rock"))
		{
			return Rock;
		}
		else
		{
			return Blank;
		}
	}
}