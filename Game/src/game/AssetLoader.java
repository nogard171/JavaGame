package game;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AssetLoader {
	public static Material loadMaterial(String materialFilename) {
		Material newMaterial = null;

		try {
			newMaterial = new Material(materialFilename);
			Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(materialFilename));
			newMaterial.setTexture(texture.getTextureID());
			newMaterial.setSize(texture.getImageWidth(), texture.getImageHeight());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newMaterial;
	}
}
