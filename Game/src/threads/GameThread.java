package threads;

import java.awt.Point;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import classes.World;
import data.AssetData;
import data.Settings;
import utils.FPS;
import utils.Input;
import utils.Loader;
import utils.View;
import utils.Window;

public class GameThread extends BaseThread {
	UIThread ui;

	World world;

	@Override
	public void setup() {
		super.setup();
		Window.create();
		Input.setup();

		ui = new UIThread();
		ui.setup();
		if (!Settings.dataLoaded) {
			Loader.load();
		}

		world = new World();
		world.setup();

		FPS.setup();

	}

	@Override
	public void update() {
		super.update();
		Window.update();
		Input.poll();
		FPS.updateFPS();
		ui.update();

		viewIndex = getChunkIndex();
		world.update();

		float speed = 1 * FPS.getDelta();

		if (Input.isKeyDown(Keyboard.KEY_A)) {
			View.x += speed;
		}

		if (Input.isKeyDown(Keyboard.KEY_D)) {
			View.x -= speed;
		}
		if (Input.isKeyDown(Keyboard.KEY_W)) {
			View.y += speed;
		}

		if (Input.isKeyDown(Keyboard.KEY_S)) {
			View.y -= speed;
		}
		/*
		 * if (Mouse.isButtonDown(0) && UIData.hover != null) { Chunk chunk =
		 * EngineData.chunks.get(UIData.hover.getChunkX() + "," +
		 * UIData.hover.getChunkY()); if (chunk != null) {
		 * 
		 * int indexX = Math.round(UIData.hover.getX() % 16); int indexY =
		 * Math.round(UIData.hover.getY() % 16); Object ground =
		 * chunk.ground[indexX][indexY]; if (ground != null) { } } } if
		 * (Input.isMousePressed(1) && UIData.hover != null) { Chunk chunk =
		 * EngineData.chunks.get(UIData.hover.getChunkX() + "," +
		 * UIData.hover.getChunkY()); if (chunk != null) { int indexX =
		 * Math.round(UIData.hover.getX() % 16); int indexY =
		 * Math.round(UIData.hover.getY() % 16); Object ground =
		 * chunk.ground[indexX][indexY]; if (ground != null) { UIThread.showMenu("test",
		 * Input.getMousePosition()); } } }
		 */
	}

	Point viewIndex;

	@Override
	public void render() {
		super.render();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, AssetData.texture.getTextureID());

		GL11.glPushMatrix();
		GL11.glTranslatef(View.x, View.y, 0);

		world.render(viewIndex);
		ui.renderOnMap();
		GL11.glPopMatrix();
		ui.render();
		Window.render();

	}

	@Override
	public void clean() {

		ui.clean();
		super.clean();
	}

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
}
