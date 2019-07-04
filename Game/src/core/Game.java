package core;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game extends Platform {

	Entity player;
	World world;

	@Override
	public void load() {
		super.load();

		world = new World();
		world.generateGround();

		try {
			Texture texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/textures/tileset.png"));

			Renderer.textures.put("tileset", texture);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Material mat = new Material();
		mat.textureName = "tileset";
		mat.textureData = new Vector4f(0, 0, 0.5f, 0.5f);

		Renderer.materials.put("grass", mat);

		Material mat2 = new Material();
		mat2.textureName = "tileset";
		mat2.textureData = new Vector4f(0, 0.5f, 0.5f, 0.5f);

		Renderer.materials.put("dirt", mat2);

	}

	@Override
	public void start() {
		super.start();
		// Mouse.setGrabbed(true);
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		float speed = 1f;

		Display.setTitle("Coord:" + this.camera.getRotation());

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.cameraController.rotateX(speed);
			// this.camera.strafe(speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.cameraController.rotateX(-speed);
			// this.camera.strafe(-speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.cameraController.rotateY(speed);
			// this.camera.move(speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.cameraController.rotateY(-speed);
			// this.camera.move(-speed);
		}
		int mouseWheel = Mouse.getDWheel();
		if (mouseWheel < 0) {
			this.cameraController.addDistance(1);
		}

		if (mouseWheel > 0) {
			this.cameraController.addDistance(-1);
		}

	}

	@Override
	public void render() {
		super.render();

		// GL11.glColor3f(1, 0, 0);

		GL11.glBegin(GL11.GL_QUADS);

		world.render(camera);

		GL11.glEnd();
	}

	@Override
	public void sync() {
		super.sync();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}
