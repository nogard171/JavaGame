package utils;

import java.awt.Font;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import classes.Chunk;
import classes.Index;
import classes.TextureData;
import classes.TextureType;
import classes.Object;
import classes.RawFontCharacter;
import classes.RawMaterial;
import classes.RawModel;
import data.AssetData;
import data.EngineData;
import ui.UIButton;
import ui.UIControl;
import ui.UIDropDown;
import ui.UIMenu;
import ui.UIMenuItem;
import ui.UISlider;
import ui.UITextField;
import ui.UIToggle;

public class Renderer {

	public static void bindTexture() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, AssetData.texture.getTextureID());
	}

	public static void unbindTexture() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public static void renderObject(Object obj, int selfX, int selfY) {
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
					int tic = 0;
					for (byte i : raw.indices) {
						Vector2f textureVec = mat.vectors[i];
						if (mat.indices.length > 0) {
							byte ti = mat.indices[tic];
							textureVec = mat.vectors[ti];
						}
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

	public static void renderModelBounds(String modelName, String materialName, int x, int z) {
		RawModel raw = AssetData.modelData.get(modelName);
		if (raw != null) {
			for (int i = 0; i < raw.boundVectors.length; i++) {
				GL11.glVertex2f(raw.boundVectors[i].x + x, (float) raw.boundVectors[i].y + z);
			}
		}
	}

	public static void renderModel(String modelName, String materialName, int x, int z) {

		GL11.glColor4f(1, 1, 1, 1);
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

	public static void renderButton(UIButton btn) {
		GL11.glColor3f(1, 1, 1);
		Renderer.renderQuad(btn.getPosition().getX(), btn.getPosition().getY(), btn.getSize().getWidth(),
				btn.getSize().getHeight(),
				(btn.getBackgroundColor() != null ? btn.getBackgroundColor() : Color.transparent));
		int width = Renderer.getTextWidth(btn.getText(), btn.getFontSize(), btn.getFontColor());
		Renderer.renderText(btn.getPosition().x + (btn.getSize().getWidth() / 2) - (width / 2), btn.getPosition().y,
				btn.getText(), btn.getFontSize(), btn.getFontColor());

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
			Renderer.renderText(control.getPosition().x, control.getPosition().y, btn.getText(), btn.getFontSize(),
					Color.black);
		}
		if (controlName.contains("MainMenu")) {

		}
		if (controlName.contains("UIPanel")) {
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
								menu.getSize().getWidth() + 5, item.getFontSize(), bgColor);
					}
					Renderer.renderText(control.getPosition().x + x, control.getPosition().y + y, item.getText(),
							item.getFontSize(), Color.white);
					y += item.getFontSize();
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

	private static final int BYTES_PER_PIXEL = 4;

	public static int loadTexture(BufferedImage image) {

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component. Only for RGBA
			}
		}

		buffer.flip(); // FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using
		// whatever OpenGL method you want, for example:

		int textureID = GL11.glGenTextures(); // Generate texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID); // Bind texture ID

		// Setup wrap mode
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		// Setup texture scaling filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		// Send texel data to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, buffer);

		// Return the texture ID so we can bind it later again
		return textureID;
	}

	public static void renderSlider(UISlider slider) {
		Color sliderColor = new Color(0.4f, 0.4f, 0.4f, 1f);
		int textWidth = Renderer.getTextWidth(slider.getName(), 20, Color.white);
		Renderer.renderText(slider.getPosition().getX() - (155) - 5, slider.getPosition().getY() - 8, slider.getName(),
				20, Color.white);
		int height = slider.getSize().getHeight() - 6;
		if (height <= 0) {
			height = 1;
		}
		Renderer.renderQuad(slider.getPosition().getX(), slider.getPosition().getY() + 3, slider.getSize().getWidth(),
				height, sliderColor);
		float volumeInt = slider.getValue();
		float volumeStep = ((float) (slider.getSize().getWidth() - 16)) / (float) slider.getMaxValue();
		Renderer.renderQuad(slider.getPosition().getX() + (float) (volumeInt * (float) volumeStep),
				slider.getPosition().getY(), 16, slider.getSize().getHeight(), new Color(0.8f, 0.8f, 0.8f, 1f));
	}

	public static void renderToggle(UIToggle toggle) {
		int textWidth = Renderer.getTextWidth(toggle.getName(), 20, Color.white);
		Renderer.renderText(toggle.getPosition().getX() - (155) - 5, toggle.getPosition().getY() - 8, toggle.getName(),
				20, Color.white);

		Renderer.renderQuad(toggle.getPosition().getX(), toggle.getPosition().getY(), 16, toggle.getSize().getHeight(),
				new Color(0.8f, 0.8f, 0.8f, 1f));
		if (toggle.getToggle()) {
			Renderer.renderQuad(toggle.getPosition().getX() + 2, toggle.getPosition().getY() + 2, 12, 12,
					new Color(0f, 0f, 0f, 1f));
		}
	}

	public static void renderDropDown(UIDropDown dropdown) {

		int textWidth = Renderer.getTextWidth(dropdown.getName(), 20, Color.white);
		Renderer.renderText(dropdown.getPosition().getX() - (155) - 5, dropdown.getPosition().getY() - 8,
				dropdown.getName(), 20, Color.white);

		Renderer.renderQuad(dropdown.getPosition().getX(), dropdown.getPosition().getY(), dropdown.getSize().getWidth(),
				dropdown.getSize().getHeight(), new Color(0.8f, 0.8f, 0.8f, 1f));

		Renderer.renderText(dropdown.getPosition().getX(), dropdown.getPosition().getY() - 5, dropdown.getValue(),
				dropdown.getSize().getHeight(), Color.black);

		LinkedList<String> resolutions = dropdown.getValues();
		if (dropdown.isShown()) {
			int y = 1;
			Renderer.renderQuad(dropdown.getPosition().getX(), dropdown.getPosition().getY() + 16,
					dropdown.getSize().getWidth(), resolutions.size() * dropdown.getSize().getHeight(),
					new Color(0.1f, 0.1f, 0.1f, 1f));
			for (String s : resolutions) {
				if (dropdown.getSelectedValue() == y) {
					Renderer.renderQuad(dropdown.getPosition().getX(),
							dropdown.getPosition().getY() + (y * dropdown.getSize().getHeight()),
							dropdown.getSize().getWidth(), dropdown.getSize().getHeight(),
							new Color(1f, 0.1f, 0.1f, 0.5f));
				}

				Renderer.renderText(dropdown.getPosition().getX(),
						dropdown.getPosition().getY() - 5 + 16 + ((y - 1) * dropdown.getSize().getHeight()), s,
						dropdown.getSize().getHeight(), Color.white);
				y++;
			}
		}
	}

	public static void renderTextField(UITextField field) {

		Renderer.renderText(field.getPosition().getX() - (160), field.getPosition().getY() - 8, field.getName(), 20,
				Color.white);
		Renderer.renderQuad(field.getPosition().getX(), field.getPosition().getY(), field.getSize().getWidth(),
				field.getSize().getHeight(), new Color(0.8f, 0.8f, 0.8f, 1f));
		Renderer.renderText(field.getPosition().getX(), field.getPosition().getY() - 5, field.getText(), 16,
				Color.black);
		if (field.isCursorActive()) {
			Renderer.renderText(
					field.getPosition().getX() + Renderer.getTextWidth(field.getText(), 16, Color.black) - 4,
					field.getPosition().getY() - 6, "|", 16, Color.black);
		}
	}
}
