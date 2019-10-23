package utils;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import classes.Chunk;
import classes.Index;
import classes.TextureData;
import classes.TextureType;
import classes.UIButton;
import classes.UIControl;
import classes.UIMenu;
import classes.Object;
import classes.RawFontCharacter;
import classes.RawMaterial;
import classes.RawModel;
import data.MaterialData;
import data.ModelData;
import data.UIData;
import data.WorldData;

public class Renderer {
	public static void renderModel(Chunk self, int x, int z) {
		if (self != null) {
			int carX = (self.index.getX() * 32) * 16;
			int carY = (self.index.getY() * 32) * 16;
			int isoX = carX - carY;
			int isoY = (carY + carX) / 2;
			int selfX = isoX;
			int selfY = isoY;
			Object obj = self.ground[x][z];
			if (obj != null) {
				RawModel raw = ModelData.modelData.get(obj.getModel());
				if (raw != null) {

					RawMaterial mat = MaterialData.materialData.get(obj.getMaterial());
					if (mat != null) {
						for (byte i : raw.indices) {
							Vector2f textureVec = mat.vectors[i];
							GL11.glTexCoord2f(textureVec.x / MaterialData.texture.getImageWidth(),
									textureVec.y / MaterialData.texture.getImageHeight());
							Vector2f vec = raw.vectors[i];
							GL11.glVertex2f(vec.x + selfX + obj.getX(), vec.y + selfY + obj.getY());
						}
					}
				}
			}
		}
	}

	public static void renderModel(String modelName, int x, int z) {
		RawModel raw = ModelData.modelData.get(modelName);
		if (raw != null) {
			RawMaterial mat = MaterialData.materialData.get(modelName);
			if (mat != null) {
				for (byte i : raw.indices) {
					Vector2f textureVec = mat.vectors[i];
					GL11.glTexCoord2f(textureVec.x / MaterialData.texture.getImageWidth(),
							textureVec.y / MaterialData.texture.getImageHeight());
					Vector2f vec = raw.vectors[i];
					GL11.glVertex2f(vec.x + x, vec.y + z);
				}
			}
		}
	}

	public static void renderModel(String modelName, String materialName, int x, int z) {
		RawModel raw = ModelData.modelData.get(modelName);
		if (raw != null) {
			RawMaterial mat = MaterialData.materialData.get(materialName);
			if (mat != null) {
				for (byte i : raw.indices) {
					Vector2f textureVec = mat.vectors[i];
					GL11.glTexCoord2f(textureVec.x / MaterialData.texture.getImageWidth(),
							textureVec.y / MaterialData.texture.getImageHeight());
					Vector2f vec = raw.vectors[i];
					GL11.glVertex2f(vec.x + x, vec.y + z);
				}
			}
		}
	}

	public static void renderModel(String modelName, float x, float z) {
		renderModel(modelName, (int) x, (int) z);
	}

	public static void renderModel(String modelName, String materialName, float x, float z) {
		renderModel(modelName, materialName, (int) x, (int) z);
	}

	public static void renderControl(UIControl control) {
		String controlName = control.getClass().getName();
		if (controlName.contains("UIButton")) {
			UIButton btn = (UIButton) control;
			GL11.glColor3f(1, 1, 1);
			GL11.glBegin(GL11.GL_TRIANGLES);
			Renderer.renderModel("BUTTON_BACK", "BUTTON", control.getPosition().x - 2, control.getPosition().y - 2);
			Renderer.renderModel("BUTTON", btn.getMaterial(), control.getPosition().x, control.getPosition().y);

			GL11.glEnd();
		}
		if (controlName.contains("UIMenu")) {
			UIMenu menu = (UIMenu) control;
			float x = menu.getPosition().getX();
			float y = menu.getPosition().getY();
			for (UIButton btn : menu.getButtons()) {
				// GL11.glColor3f(1, 1, 1);
				// GL11.glBegin(GL11.GL_TRIANGLES);

				// GL11.glEnd();
				y += btn.getSize().getHeight();
			}
		}
	}

	public static void renderText(String text, float x, float y, int size, Color color) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
		for (int c = 0; c < text.length(); c++) {
			float space = ((size*3) * c);
			renderCharacter(String.valueOf(text.charAt(c)), x + space, y, size);
		}

		GL11.glEnd();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, MaterialData.texture.getTextureID());
	}

	// private static HashMap<String, Integer> fontCharacters = new HashMap<String,
	// Integer>();

	private static void renderCharacter(String c, float x, float y, int size) {

		RawFontCharacter rawChar = UIData.fontCharacters.get(c);
		if (rawChar != null) {
			if (rawChar.getDisplayList() == -1) {
				int displayListGen = GL11.glGenLists(1);
				GL11.glNewList(displayListGen, GL11.GL_COMPILE_AND_EXECUTE);

				int cx = 0;
				int cy = 0;
				float xSize = size * 0.6f;
				float ySize = size;

				for (Byte cell : rawChar.getBlocks()) {
					if (cell == 1) {
						GL11.glVertex2f(x + (cx * xSize), y + (cy * ySize));
						GL11.glVertex2f(x + xSize + (cx * xSize), y + (cy * ySize));
						GL11.glVertex2f(x + xSize + (cx * xSize), y + ySize + (cy * ySize));
						GL11.glVertex2f(x + (cx * xSize), y + ySize + (cy * ySize));
					}
					if (cx >= 3) {
						cx = 0;
						cy++;
					} else {
						cx++;
					}
				}

				GL11.glEndList();

				// calling display list does not work for some reason //
				// rawChar.setDisplayList(displayListGen);
				UIData.fontCharacters.put(c, rawChar);

			} else {
				GL11.glCallList(rawChar.getDisplayList());
			}
		}

	}

	public static void renderGrid(int indexX, int indexY) {
		int cartX = indexX * 32;
		int cartZ = indexY * 32;

		int isoX = cartX - cartZ;
		int isoZ = (cartX + cartZ) / 2;

		GL11.glVertex2i(isoX, isoZ);
		isoX = (cartX + 32) - cartZ;
		isoZ = ((cartX + 32) + cartZ) / 2;
		GL11.glVertex2i(isoX, isoZ);

		isoX = (cartX + 32) - (cartZ + 32);
		isoZ = ((cartX + 32) + (cartZ + 32)) / 2;
		GL11.glVertex2i(isoX, isoZ);

		isoX = (cartX) - (cartZ + 32);
		isoZ = ((cartX) + (cartZ + 32)) / 2;
		GL11.glVertex2i(isoX, isoZ);
	}
}
