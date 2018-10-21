package states;

import org.newdawn.slick.Color;

import classes.GLPosition;
import classes.GLQuadData;
import utils.GLHandler;

public class TitleScreen {
	public static void Setup() {
	}

	public static void Update() {

	}

	public static void Render() {
		GLHandler.RenderString(400, 300, "Title Screen", Color.black, 32);
	}
}
