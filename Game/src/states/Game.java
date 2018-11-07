package states;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

import classes.GLChunk;
import classes.GLGameState;
import classes.GLModelData;
import classes.GLPosition;
import classes.GLQuadData;
import classes.GLSize;
import components.GLShader;
import components.GLTransform;
import engine.GLDisplay;
import engine.GLInput;
import utils.GLChunkManager;
import utils.GLDebug;
import utils.GLFPS;
import utils.GLHandler;

public class Game {
	private static GLQuadData quadData;

	static GLModelData model;

	public static void Setup() {
		quadData = new GLQuadData();
		quadData.setName("grass_top");
		GLPosition[] vectors = { new GLPosition(32, 0), new GLPosition(64, 16), new GLPosition(32, 32),
				new GLPosition(0, 16) };
		quadData.SetVectors(vectors);

		byte[] indices = { 0, 1, 2, 3 };
		quadData.SetIndices(indices);

		GLHandler.AddQuad(quadData);

		model = new GLModelData();
		model.setName("grass");
		model.setQuadName("grass_top");
		model.setStart(new GLPosition(0.5f, 0));
		model.setSize(new GLSize(0.5f, 0.5f));

		shader = new GLShader("basic.vert", "basic.frag");

		for (int x = -10; x < 10; x++) {
			for (int y = -10; y < 10; y++) {
				GLChunkManager.chunks.add(new GLChunk(x, y));

			}
		}

		GLChunkManager.UpdateChunks();

	}

	public static void Update() {

	}

	static GLShader shader;
	static int dl = -1;

	public static void Render() {
		shader.Run();

		GLChunkManager.RenderChunks(shader);
		shader.Stop();

	}
}
