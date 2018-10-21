package engine;

import org.newdawn.slick.Color;

import classes.GLGameState;
import states.Game;
import states.TitleScreen;
import utils.GLHandler;

public class GLStateHandler {

	public static void SetupState() {
		TitleScreen.Setup();
		Game.Setup();
	}

	public static void UpdateState(GLGameState gameState) {
		if (gameState == GLGameState.TITLE_SCREEN) {
			TitleScreen.Update();
		}
		if (gameState == GLGameState.GAME) {
			Game.Update();
		}
	}

	public static void RenderState(GLGameState gameState) {
		if (gameState == GLGameState.TITLE_SCREEN) {
			TitleScreen.Render();
		}
		if (gameState == GLGameState.GAME) {
			Game.Render();
		}
	}
}
