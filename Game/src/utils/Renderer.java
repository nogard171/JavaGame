package utils;

import java.awt.Font;
import java.awt.Polygon;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import classes.Chunk;
import classes.Index;
import classes.TextureData;
import classes.TextureType;
import classes.UIButton;
import classes.UIControl;
import classes.UIMenu;
import classes.UIMenuItem;
import classes.Object;
import classes.RawFontCharacter;
import classes.RawMaterial;
import classes.RawModel;
import data.AssetData;
import data.EngineData;

public class Renderer {

	public static void bindTexture() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, AssetData.texture.getTextureID());
	}

	public static void unbindTexture() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

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
				RawModel raw = AssetData.modelData.get(obj.getModel());
				if (raw != null) {
					if (obj.bounds == null) {
						obj.bounds = new Polygon();
						for (Vector2f vec : raw.boundVectors) {
							obj.bounds.addPoint((int) (vec.x + selfX + obj.getX()), (int) (vec.y + selfY + obj.getY()));

						}
					}
					RawMaterial mat = AssetData.materialData.get(obj.getMaterial());
					if (mat != null) {
						for (byte i : raw.indices) {
							Vector2f textureVec = mat.vectors[i];
							GL11.glTexCoord2f(textureVec.x / AssetData.texture.getImageWidth(),
									textureVec.y / AssetData.texture.getImageHeight());
							Vector2f vec = raw.vectors[i];
							GL11.glVertex2f(vec.x + selfX + obj.getX(), vec.y + selfY + obj.getY());
						}
					}
				}
			}
			obj = null;
			obj = self.objects[x][z];
			if (obj != null) {
				// System.out.println("Vec:" + obj.getModel());
				RawModel raw = AssetData.modelData.get(obj.getModel());
				if (raw != null) {
					if (obj.bounds == null) {
						obj.bounds = new Polygon();
						for (Vector2f vec : raw.boundVectors) {
							obj.bounds.addPoint((int) (vec.x + selfX + obj.getX()), (int) (vec.y + selfY + obj.getY()));
						}
					}
					RawMaterial mat = AssetData.materialData.get(obj.getMaterial());
					if (mat != null) {
						int tic = 0;
						for (byte i : raw.indices) {
							byte ti = mat.indices[tic];
							Vector2f textureVec = mat.vectors[ti];
							GL11.glTexCoord2f(textureVec.x / AssetData.texture.getImageWidth(),
									textureVec.y / AssetData.texture.getImageHeight());
							Vector2f vec = raw.vectors[i];
							GL11.glVertex2f(vec.x + selfX + obj.getX(), vec.y + selfY + obj.getY());
							tic++;
						}
					}
				}
			}
		}
	}

	public static void renderModel(String modelName, int x, int z) {
		RawModel raw = AssetData.modelData.get(modelName);
		if (raw != null) {
			RawMaterial mat = AssetData.materialData.get(modelName);
			if (mat != null) {
				for (byte i : raw.indices) {
					Vector2f textureVec = mat.vectors[i];
					GL11.glTexCoord2f(textureVec.x / AssetData.texture.getImageWidth(),
							textureVec.y / AssetData.texture.getImageHeight());
					Vector2f vec = raw.vectors[i];
					GL11.glVertex2f(vec.x + x, vec.y + z);
				}
			}
		}
	}

	public static void renderModel(String modelName, String materialName, int x, int z) {
		RawModel raw = AssetData.modelData.get(modelName);
		if (raw != null) {
			RawMaterial mat = AssetData.materialData.get(materialName);
			if (mat != null) {
				for (byte i : raw.indices) {
					Vector2f textureVec = mat.vectors[i];
					GL11.glTexCoord2f(textureVec.x / AssetData.texture.getImageWidth(),
							textureVec.y / AssetData.texture.getImageHeight());
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
			Renderer.renderText(control.getPosition().x, control.getPosition().y, btn.getText(), 12, Color.black);
		}
		if (controlName.contains("UIMenu")) {
			UIMenu menu = (UIMenu) control;
			float x = 0;
			float y = 0;

			Renderer.renderQuad(menu.getPosition().getX() - 1, menu.getPosition().getY() + 3,
					menu.getSize().getWidth() + 5, menu.getSize().getHeight(), new Color(0, 0, 0, 0.5f));

			for (UIControl temp : menu.getControls()) {
				UIMenuItem item = (UIMenuItem) temp;
				if (item != null) {
					Color bgColor = item.getBackgroundColor();
					if (bgColor != null) {
						Renderer.renderQuad(control.getPosition().x + x - 1, control.getPosition().y + y + 3,
								menu.getSize().getWidth() + 5, 12, bgColor);
					}
					Renderer.renderText(control.getPosition().x + x, control.getPosition().y + y, item.getText(), 12,
							Color.white);
					y += temp.getSize().getHeight();
				}
			}
		}
	}

	public static void renderQuad(float x, float y, int width, int height, Color color) {
		unbindTexture();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(color.r, color.g, color.b, color.a);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		bindTexture();
	}

	public static void renderGrid(int indexX, int indexY) {
		unbindTexture();
		GL11.glBegin(GL11.GL_QUADS);
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
		GL11.glEnd();
		bindTexture();
	}

	public static void renderText(float x, float y, String text, int fontSize, Color color) {
		renderText((int) x, (int) y, text, fontSize, color);
	}

	public static void renderText(int x, int y, String text, int fontSize, Color color) {
		unbindTexture();
		TrueTypeFont font = EngineData.fonts.get(fontSize + "," + color);
		if (font == null) {
			Font awtFont = new Font("Courier", Font.PLAIN, fontSize);
			EngineData.fonts.put(fontSize + "," + color, new TrueTypeFont(awtFont, false));
		}
		if (font != null) {
			TextureImpl.bindNone();
			font.drawString(x, y, text, color);

		}
		bindTexture();
	}

	public static int getTextWidth(String text, int fontSize, Color color) {
		int width = -1;
		TrueTypeFont font = EngineData.fonts.get(fontSize + "," + color);
		if (font == null) {
			Font awtFont = new Font("Courier", Font.PLAIN, fontSize);
			EngineData.fonts.put(fontSize + "," + color, new TrueTypeFont(awtFont, false));
		}
		if (font != null) {
			TextureImpl.bindNone();
			width = font.getWidth(text);
		}
		return width;
	}
}
