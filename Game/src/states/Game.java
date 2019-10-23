package states;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import classes.GLGameState;
import classes.GLPosition;
import classes.GLQuadData;
import components.GLTransform;
import engine.GLDisplay;
import engine.GLInput;
import utils.GLDebug;
import utils.GLFPS;
import utils.GLHandler;

public class Game {
	private static GLQuadData quadData;

	public static void Setup() {
		quadData = new GLQuadData("test");
		GLPosition[] vectors = { new GLPosition(0, 0), new GLPosition(32, 0), new GLPosition(32, 32),
				new GLPosition(0, 32) };
		quadData.SetVectors(vectors);

		byte[] indices = { 0, 1, 2, 3 };
		quadData.SetIndices(indices);
	}

	public static void Update() {

	}

	public static void Render() {
		GLHandler.SetRenderColor(1, 0, 0, 1);
		GL11.glPushMatrix();
		for (int i = 0; i < 10; i++) {
			GL11.glTranslatef(i * 32, 0, 0);
			GLHandler.RenderQuad(quadData);

			GL11.glTranslatef(i * -32, 0, 0);
		}
		GL11.glPopMatrix();
	}
}
