package states;

import org.lwjgl.input.Keyboard;
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
		quadData = new GLQuadData();
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

		GLHandler.RenderQuad(quadData);
	}
}
