package threads;

import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Action;
import classes.Index;
import classes.MouseIndex;
import classes.Size;
import classes.View;
import classes.World;
import data.EngineData;
import data.Settings;
import ui.MainMenu;
import ui.UIButton;
import ui.UIControl;
import ui.UIMenu;
import ui.UIMenuItem;
import data.EngineData;
import utils.FPS;
import utils.Input;
import utils.Loader;
import classes.Object;
import utils.Renderer;
import utils.Window;

public class UIThread extends BaseThread {
	MainMenu mainMenu;

	@Override
	public void setup() {
		super.setup();
		mainMenu = new MainMenu();
		mainMenu.setup();
		// EngineData.controls.put("mainMenuControl", mainMenu);

		UIButton test = new UIButton();
		test.setName("move");
		test.setText("Move");
		test.setPosition(new Vector2f(100, 100));
		test.setSize(new Size(32, 32));
		test.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				if (mouseButton == 0) {
					System.out.println("Mouse Click Left");
				}
				if (mouseButton == 1) {
					System.out.println("Mouse Click Right");
				}
				if (mouseButton == 2) {
					System.out.println("Mouse Click Middle");
				}
			}
		});

		// EngineData.controls.put(test.getName(), test);
	}

	@Override
	public void update() {
		super.update();

		mainMenu.update();

		if (Input.isKeyPressed(Keyboard.KEY_F1)) {
			EngineData.showTelematry = !EngineData.showTelematry;
		}
		if (Input.isKeyPressed(Keyboard.KEY_F2)) {
			Loader.load();
			World.rebuild();
			EngineData.dataLoaded = true;
		}
		if (Input.isMousePressed(1)) {
			showMenu("test", Input.getMousePosition());
		}
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

	@Override
	public void render() {
		super.render();
		mainMenu.render();
		objectsHovered = World.getHoveredObjects();
		if (EngineData.showTelematry) {
			Vector2f debugPosition = new Vector2f(Window.getWidth() - 200, 0);
			Renderer.renderQuad(debugPosition.x, debugPosition.y, 200, 100, new Color(0, 0, 0, 0.5f));
			Renderer.renderText(debugPosition.x, debugPosition.y, "FPS:" + FPS.currentfps, 12, Color.white);
			Renderer.renderText(debugPosition.x, debugPosition.y + 12, "Data Loaded:" + EngineData.dataLoaded, 12,
					Color.white);
			Renderer.renderText(debugPosition.x, debugPosition.y + 24,
					"Chunks:" + EngineData.renderedChunks.size() + "/" + EngineData.chunks.size(), 12, Color.white);
			Renderer.renderText(debugPosition.x, debugPosition.y + 36, "Blocked Input:" + EngineData.blockInput, 12,
					Color.white);

			if (objectsHovered.size() > 0) {
				Renderer.renderText(debugPosition.x - 200, debugPosition.y, "Hover Index:", 12, Color.white);
				int y = 12;
				for (int i = objectsHovered.size() - 1; i >= 0; i--) {
					Object obj = objectsHovered.get(i);
					if (obj != null) {
						Renderer.renderText(debugPosition.x - 200, y, obj.toString(), 12, Color.white);
						y += 12;
					}
				}
			}
		}
		EngineData.pauseGame = EngineData.showMainMenu;

	}

	static LinkedList<Object> objectsHovered;

	public void renderOnMap() {
		if (EngineData.showTelematry) {
			if (objectsHovered != null) {
				if (objectsHovered.size() > 0) {
					int i = 0;
					for (Object obj : objectsHovered) {
						int carX = obj.getIndex().getX() * 32;
						int carY = obj.getIndex().getY() * 32;
						int isoX = carX - carY;
						int isoY = (carY + carX) / 2;
						if (i == objectsHovered.size() - 1) {
							GL11.glColor4f(1, 0, 0, 0.5f);
						} else {
							GL11.glColor4f(0.25f, 0.25f, 0.25f, 0.5f);
						}
						GL11.glDisable(GL11.GL_TEXTURE_2D);
						GL11.glBegin(GL11.GL_TRIANGLES);
						Renderer.renderModel(obj.getModel(), obj.getMaterial(), isoX, isoY);
						GL11.glEnd();
						GL11.glEnable(GL11.GL_TEXTURE_2D);
						i++;
					}
				}
			}
		}
	}

	@Override
	public void clean() {

		super.clean();
	}

	public static void showMenu(String string, Vector2f position) {

		UIControl temp = EngineData.controls.get(string);
		if (temp != null) {
			UIMenu tempMenu = (UIMenu) temp;
			if (tempMenu != null) {
				tempMenu.clear();
				for (Object obj : objectsHovered) {
					UIMenuItem testItem = new UIMenuItem();
					testItem.setName("Inspect:" + obj.getModel());
					testItem.setText("Inspect:" + obj.getModel());
					testItem.onEvent(new Action() {
						@Override
						public void onMouseClick(UIControl self, int mouseButton) {
							if (mouseButton == 0) {
								System.out.println("Mouse Click Left" + self.getName());
							}
							if (mouseButton == 1) {
								System.out.println("Mouse Click Right");
							}
							if (mouseButton == 2) {
								System.out.println("Mouse Click Middle");
							}
						}

						@Override
						public void onMouseHover(UIControl self) {
							UIMenuItem temp = (UIMenuItem) self;
							if (temp != null) {
								temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
							}
						}

						@Override
						public void onMouseExit(UIControl self) {
							UIMenuItem temp = (UIMenuItem) self;
							if (temp != null) {
								temp.setBackgroundColor(null);
							}
						}
					});
					tempMenu.add(testItem);
					testItem = new UIMenuItem();
					testItem.setName("Chop:" + obj.getModel());
					testItem.setText("Chop:" + obj.getModel());
					testItem.onEvent(new Action() {
						@Override
						public void onMouseClick(UIControl self, int mouseButton) {
							if (mouseButton == 0) {
								System.out.println("Mouse Click Left" + self.getName());
							}
							if (mouseButton == 1) {
								System.out.println("Mouse Click Right");
							}
							if (mouseButton == 2) {
								System.out.println("Mouse Click Middle");
							}
						}

						@Override
						public void onMouseHover(UIControl self) {
							UIMenuItem temp = (UIMenuItem) self;
							if (temp != null) {
								temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
							}
						}

						@Override
						public void onMouseExit(UIControl self) {
							UIMenuItem temp = (UIMenuItem) self;
							if (temp != null) {
								temp.setBackgroundColor(null);
							}
						}
					});
					tempMenu.add(testItem);
				}
			}

			temp.setPosition(new Vector2f(position.x, position.y - 10));
			temp.toggle();
			EngineData.pauseGame = true;
		}
	}

}
