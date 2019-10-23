package threads;

import java.awt.Point;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import classes.Chunk;
import classes.Index;
import classes.MouseIndex;
import classes.TextureType;
import classes.World;
import classes.Object;
import data.MaterialData;
import data.ModelData;
import data.UIData;
import data.WorldData;
import utils.APathFinder;
import utils.FPS;
import utils.Loader;
import utils.Renderer;
import utils.Ticker;
import utils.View;
import utils.Window;

public class GameThread extends BaseThread {
	UIThread ui;

	World world;

	@Override
	public void setup() {
		super.setup();
		Window.create();

		ui = new UIThread();
		ui.setup();

		Loader.loadMaterials();
		Loader.loadTextures();
		

		world = new World();
		world.setup();

		FPS.setup();

	}

	@Override
	public void update() {
		super.update();
		ui.update();

		Window.update();
		FPS.updateFPS();
		pollHover();

		viewIndex = getChunkIndex();
		world.update();

		float speed = 1 * FPS.getDelta();

		if (Window.isKeyDown(Keyboard.KEY_A)) {
			View.x += speed;
		}

		if (Window.isKeyDown(Keyboard.KEY_D)) {
			View.x -= speed;
		}
		if (Window.isKeyDown(Keyboard.KEY_W)) {
			View.y += speed;
		}

		if (Window.isKeyDown(Keyboard.KEY_S)) {
			View.y -= speed;
		}

		if (Mouse.isButtonDown(0) && hover != null) {
			Chunk chunk = WorldData.chunks.get(hover.getChunkX() + "," + hover.getChunkY());
			if (chunk != null) {

				int indexX = Math.round(hover.getX() % 16);
				int indexY = Math.round(hover.getY() % 16);
				Object ground = chunk.ground[indexX][indexY];
				if (ground != null) {
				}
			}			
		}
		if (Mouse.isButtonDown(1) && hover != null) {
			Chunk chunk = WorldData.chunks.get(hover.getChunkX() + "," + hover.getChunkY());
			if (chunk != null) {
				int indexX = Math.round(hover.getX() % 16);
				int indexY = Math.round(hover.getY() % 16);
				Object ground = chunk.ground[indexX][indexY];
				if (ground != null) {
					UIThread.showMenu("test");
				}
			}
		}
		/*
		if (mouseStart != null && mouseEnd != null) {
			System.out.println("start: " + mouseStart + "," + mouseEnd);
			WorldData.path = APathFinder.find(mouseStart, mouseEnd);
			world.refresh();
			mouseStart = null;
			mouseEnd = null;
		}*/
	}

	MouseIndex hover;

	private Point getChunkIndex() {
		int cartX = (Window.getWidth() / 2) - View.x;
		int cartY = (Window.getHeight() / 2) - View.y;
		int isoX = cartX / 2 + (cartY);
		int isoY = cartY - cartX / 2;
		int indexX = (int) Math.floor((float) isoX / (float) 32);
		int indexY = (int) Math.floor((float) isoY / (float) 32);

		int chunkX = (int) Math.floor(indexX / 16);
		int chunkY = (int) Math.floor(indexY / 16);
		if (indexX < 0) {
			chunkX--;
		}
		if (indexY < 0) {
			chunkY--;
		}

		return new Point(chunkX, chunkY);
	}

	private void pollHover() {
		int cartX = Window.getMouseX() - View.x;
		int cartY = Window.getMouseY() - View.y;
		int isoX = cartX / 2 + (cartY);
		int isoY = cartY - cartX / 2;
		int indexX = (int) Math.floor((float) isoX / (float) 32);
		int indexY = (int) Math.floor((float) isoY / (float) 32);

		int chunkX = (int) Math.floor(indexX / 16);
		int chunkY = (int) Math.floor(indexY / 16);
		if (indexX < 0) {
			chunkX--;
		}
		if (indexY < 0) {
			chunkY--;
		}

		hover = new MouseIndex(indexX, indexY, chunkX, chunkY);
	}

	Point viewIndex;

	@Override
	public void render() {
		super.render();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, MaterialData.texture.getTextureID());

		GL11.glPushMatrix();
		GL11.glTranslatef(View.x, View.y, 0);

		world.render(viewIndex);
		if (hover != null) {
			/*
			 * int chunkX = (int) Math.floor(hover.getX() / 16); int chunkY = (int)
			 * Math.floor(hover.getY() / 16); if (hover.getX() < 0) { chunkX--; } if
			 * (hover.getY() < 0) { chunkY--; }
			 * 
			 * Chunk chunk = WorldData.chunks.get(chunkX + "," + chunkY); if (chunk != null)
			 * { int indexX = Math.round(hover.getX() % 16); int indexY =
			 * Math.round(hover.getY() % 16); int height = chunk.data[indexX][indexY];
			 * System.out.println("X: " + indexX + "," + indexY + "=" + height); }
			 */
			GL11.glColor4f(1f, 0, 0, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			Renderer.renderGrid(hover.getX(), hover.getY());
			GL11.glEnd();
		}
		GL11.glPopMatrix();
		ui.render();
		Window.render();

	}

	@Override
	public void clean() {

		ui.clean();
		super.clean();
	}
}
