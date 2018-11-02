package states;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import classes.GLGameState;
import classes.GLModelData;
import classes.GLPosition;
import classes.GLQuadData;
import classes.GLSize;
import components.GLShader;
import components.GLTransform;
import engine.GLDisplay;
import engine.GLInput;
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

	}

	public static void Update() {

	}

	static GLShader shader;

	public static void Render() {
		GL11.glPushMatrix();
		shader.Run();
		//GLHandler.SetRenderColor(1, 0, 0, 1);
		float[] color = { 1, 0, 0, 1 };
		shader.sendUniform4f("vertColor", color);
		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50; y++) {
				float[] position = { (x-y) * 32, (y+x) * 16 };
				shader.sendUniform2f("position", position);
				GLHandler.RenderModel(model);
				
			}
		}
		 GL11.glPopMatrix();
		
		shader.Stop();
	}
}
