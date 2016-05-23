package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class TextureHandler {
	public BufferedImage textureLoad(String location) {

		try {
			return ImageIO.read(getClass().getClassLoader().getResourceAsStream( location));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block

			// e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
	}
}
