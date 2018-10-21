import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import classes.GLGameState;
import classes.GLPosition;
import classes.GLQuadData;
import components.GLTransform;
import engine.GLDisplay;
import engine.GLInput;
import engine.GLStateHandler;
import utils.GLDebug;
import utils.GLFPS;
import utils.GLHandler;

public class Application {
	private GLDisplay display;
	private GLGameState gameState = GLGameState.GAME;

	public void Start() {
		this.display = new GLDisplay(800, 600);
		this.display.Create();
		this.display.SetTitle("test");

		GLInput.Setup();

		GLDebug.enabled(true);

		GLFPS.Start();
		GLFPS.GetDelta();

		Setup();
		GameLoop();
	}

	public void Setup() {
		GLStateHandler.SetupState();
	}

	public void GameLoop() {
		while (!display.IsClosed()) {
			this.Update();
			this.Render();
		}
	}

	public void Update() {
		display.Update();
		GLFPS.Update();
		GLInput.Update();

		if (GLInput.IsKeyDown(Keyboard.KEY_F1)) {
			GLDebug.enabled(true);
		}
		if (GLInput.IsKeyDown(Keyboard.KEY_F2)) {
			GLDebug.enabled(false);
		}
		GLStateHandler.UpdateState(gameState);
	}

	public void Destroy() {
	}

	public void Render() {
		GLStateHandler.RenderState(gameState);

		if (GLDebug.IsEnabled()) {
			GLDebug.RenderBackground(0, 0, 200, 50);
			GLDebug.RenderString(0, 0, "FPS: " + GLFPS.GetFPS());
			GLDebug.RenderString(0, 12,
					"Mouse Position: " + GLInput.GetMousePosition().GetX() + "," + GLInput.GetMousePosition().GetY());
			GLDebug.RenderString(0, 24, "Object Count: ");
		}
	}
}
