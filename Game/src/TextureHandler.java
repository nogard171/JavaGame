import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureHandler
{
    public static HashMap<String, Texture> textures = new HashMap<String, Texture>();

    public TextureHandler()
    {
	loadTextures("resources/images/");
    }

    public void loadTextures(String dir)
    {
	File folder = new File(dir);
	File[] listOfFiles = folder.listFiles();
	Texture texture = null;
	for (int i = 0; i < listOfFiles.length; i++)
	{
	    if (listOfFiles[i].isFile())
	    {
		if (listOfFiles[i].getName().toLowerCase().endsWith("png"))
		{
		    try
		    {
			texture = TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream(listOfFiles[i].getPath()));
		    }
		    catch (IOException e)
		    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    String filename = listOfFiles[i].getName().split("\\.")[0];
		    textures.put(filename, texture);
		    System.out.println("File " + filename);
		}
	    }
	    else if (listOfFiles[i].isDirectory())
	    {
		loadTextures(dir + listOfFiles[i].getName() + "/");
		System.out.println("Directory " + listOfFiles[i].getName());
	    }
	}

    }
    public static Texture getTexture(String key)
    {
	return textures.get(key);
    }
}
