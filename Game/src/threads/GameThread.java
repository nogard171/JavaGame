package threads;

import java.awt.Point;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import classes.View;
import classes.World;
import data.AssetData;
import data.EngineData;
import data.Settings;
import utils.FPS;
import utils.Input;
import utils.Loader;
import utils.Window;

public class GameThread extends BaseThread {
	UIThread ui;
	BackEndThread backend;
	View view;

	World world;

	@Override
	public void setup() {
		super.setup();
		Window.create();
		view = new View();
		Input.setup();

		if (Settings.localChunks) {
			backend = new BackEndThread();
			backend.setup();
		}

		ui = new UIThread();
		ui.setup();

		if (!EngineData.dataLoaded) {
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

		if (Settings.localChunks) {
			backend.update();
		}
		if (!EngineData.pauseGame) {
			view.update();
			world.update();

			float speed = 1 * FPS.getDelta();
			float x = 0;
			float y = 0;
			if (Input.isKeyDown(Keyboard.KEY_A)) {
				x += speed;
			}

			if (Input.isKeyDown(Keyboard.KEY_D)) {
				x -= speed;
			}
			if (Input.isKeyDown(Keyboard.KEY_W)) {
				y += speed;
			}

			if (Input.isKeyDown(Keyboard.KEY_S)) {
				y -= speed;
			}
			view.move(x, y);
		}
	}

	@Override
	public void render() {
		super.render();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, AssetData.texture.getTextureID());

		GL11.glPushMatrix();
		GL11.glTranslatef(View.getX(), View.getY(), 0);
		world.render(View.getIndex());
		ui.renderOnMap();
		GL11.glPopMatrix();

		ui.render();
		Window.render();

	}

	@Override
	public void clean() {
		ui.clean();
		world.clean();
		super.clean();
	}

}
