package game;

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

import classes.GLChunk;
import classes.GLMaterialData;
import classes.GLModel;
import classes.GLModelData;
import classes.GLObject;
import classes.GLPosition;
import classes.GLSize;
import classes.GLSprite;
import classes.GLVelocity;
import engine.GLCamera;
import engine.GLDisplay;
import engine.GLRenderer;
import engine.GLShader;
import utils.GLLoader;

public class Game extends GLDisplay {

	// GLShader shader;
	GLCamera camera;

	@Override
	public void Setup() {
		super.Setup();
		camera = new GLCamera(800, 600);
		GLShader newShader = new GLShader("basic.vert", "basic.frag");
		GLData.shader = newShader;

		GLLoader.loadModels();

		chunk = new GLChunk();
		modelData.vectors.add(new GLPosition(0, 0));
		modelData.vectors.add(new GLPosition(0, 64));
		modelData.vectors.add(new GLPosition(64, 64));
		modelData.vectors.add(new GLPosition(64, 0));

		modelData.indices.add((byte) 0);
		modelData.indices.add((byte) 1);
		modelData.indices.add((byte) 2);

		modelData.indices.add((byte) 0);
		modelData.indices.add((byte) 3);
		modelData.indices.add((byte) 2);

		materialData.textureVectors.add(new GLPosition(0.5f, 0));
		materialData.textureVectors.add(new GLPosition(0.5f, 0.5f));
		materialData.textureVectors.add(new GLPosition(1, 0.5f));
		materialData.textureVectors.add(new GLPosition(1, 0));

		materialData.textureIndices.add((byte) 0);
		materialData.textureIndices.add((byte) 1);
		materialData.textureIndices.add((byte) 2);

		materialData.textureIndices.add((byte) 0);
		materialData.textureIndices.add((byte) 3);
		materialData.textureIndices.add((byte) 2);

		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/textures/tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	Texture texture;
	GLModelData modelData = new GLModelData();

	GLMaterialData materialData = new GLMaterialData();

	@Override
	public void UpdateWindow(int width, int height) {
		camera.size = new GLSize(width, height);
	}

	@Override
	public void Update(float delta) {
		super.Update(delta);
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

		camera.Move(new GLVelocity(xSpeed, ySpeed));
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					super.close = true;
				}
			}
		}
	}

	GLChunk chunk;

	@Override
	public void Render() {
		super.Render();
		GLShader shader = GLData.shader;
		if (shader != null) {
			shader.Run();

			float[] colorData = { 1, 1, 1, 1 };
			shader.sendUniform4f("vertColor", colorData);

			float[] viewPosition = { camera.position.x, camera.position.y, 0 };

			shader.sendUniform2f("view", viewPosition);

			GLRenderer.RenderChunk(chunk);

			// GL11.glColor3f(1, 0, 0);

			/*
			 * GLModel mod = GLData.models.get("GRASS");
			 * 
			 * int id = mod.textureID;//this.texture.getTextureID();
			 * System.out.println("ID: " +id);
			 * 
			 * shader.sendTexture("myTexture", id);
			 * 
			 * GLRenderer.RenderModel(modelData, materialData);
			 */

		}
	}

	@Override
	public void Destroy() {

		super.Destroy();
	}

}
