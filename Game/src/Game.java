import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game extends GLDisplay {

	HashMap<String, GLTexture> textures = new HashMap<String, GLTexture>();

	@Override
	public void Setup() {
		super.Setup();
		GLShader shader = new GLDataHub().shader;
		shader = new GLShader("basic.vert", "basic.frag");
		try {
			new GLDataHub().texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/textures/tiles.png"));
			shader.sendTexture("myTexture", new GLDataHub().texture.getTextureID());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new GLDataHub().shader = shader;
		new GLDataHub().models.put("GRASS", new GLLoader().loadModel("ground.obj"));
		new GLDataHub().models.put("PLAYER", new GLLoader().loadModel("player.obj"));
		new GLDataHub().camera = new GLCamera(Display.getWidth(), Display.getHeight());

	}

	@Override
	public void UpdateWindow(int width, int height) {
		new GLDataHub().camera.size = new Vector2f(width, height);
	}

	@Override
	public void Update(float delta) {
		super.Update(delta);
		GLCamera camera = new GLDataHub().camera;
		float speed = (delta + 1) * 0.5f;

		float xSpeed = 0;
		float ySpeed = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			xSpeed = speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xSpeed = -speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			ySpeed = speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			ySpeed = -speed;
		}
		camera.Move(new Vector2f(xSpeed, ySpeed));
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					super.close = true;
				}
			}
		}
	}

	@Override
	public void Render() {
		super.Render();
		GLCamera camera = new GLDataHub().camera;
		GLShader shader = new GLDataHub().shader;

		shader.Run();

		float[] colorData = { 1, 1, 1, 1 };
		shader.sendUniform4f("vertColor", colorData);

		float[] viewPosition = { camera.position.x, camera.position.y, 0 };

		shader.sendUniform2f("view", viewPosition);

		GL11.glCallList(new GLDataHub().models.get("GRASS").displayListID);
	}

	@Override
	public void Destroy() {

		super.Destroy();
	}

}
