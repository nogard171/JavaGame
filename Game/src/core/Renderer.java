package core;

import java.awt.Color;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Renderer {

	public static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	public static HashMap<String, Material> materials = new HashMap<String, Material>();

	public static void renderObject(Entity obj) {
		Color color = Color.white;
		switch (obj.getType()) {
		case GRASS:
			color = Color.green;
			break;
		case DIRT:
			color = Color.red;
			break;

		}

		// GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(),
		// color.getAlpha());
		GL11.glColor4f(1, 1, 1, 1);
		Material mat = materials.get(obj.getMaterialName());

		if (mat != null) {
			Texture texture = textures.get(mat.textureName);

			if (texture != null) {

				texture.bind();
				GL11.glTexCoord2f(mat.textureData.getX(), mat.textureData.y + mat.textureData.getZ());
				GL11.glVertex3f(obj.getPosition().getX(), obj.getPosition().getY(), obj.getPosition().getZ() + 1);
				GL11.glTexCoord2f(mat.textureData.getX() + mat.textureData.getW(),
						mat.textureData.y + mat.textureData.getZ());
				GL11.glVertex3f(obj.getPosition().getX() + 1, obj.getPosition().getY(), obj.getPosition().getZ() + 1);
				GL11.glTexCoord2f(mat.textureData.getX() + mat.textureData.getW(), mat.textureData.y);
				GL11.glVertex3f(obj.getPosition().getX() + 1, obj.getPosition().getY(), obj.getPosition().getZ());
				GL11.glTexCoord2f(mat.textureData.getX(), mat.textureData.y);
				GL11.glVertex3f(obj.getPosition().getX(), obj.getPosition().getY(), obj.getPosition().getZ());

			}
		}
	}
}
