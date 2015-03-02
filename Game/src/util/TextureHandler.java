package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class TextureHandler {
	public static BufferedImage textureLoad(String location) {

		try {
			return ImageIO.read(new URL("http://localhost" + location));
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
