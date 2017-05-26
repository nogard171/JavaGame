package Utils;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GLTextureLoader {
	public static int getTextureId(String textureFile) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(textureFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture.getTextureID();
	}
}
