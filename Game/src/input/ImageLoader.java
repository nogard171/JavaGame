package input;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/*
 * example
 * imageLoader.getImage(<location of the image in the resources>);
 */
public class ImageLoader 
{
	//this return the image based on the string
	public static BufferedImage getImageFromResources(String image)
	{
		//get the current directory
		String workingDir = System.getProperty("user.dir");
		//try setting the texture to an image
	    try
		{
	    	return ImageIO.read(new File(workingDir +image));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return null;
		}
	}
}