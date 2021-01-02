package utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import core.GameData;
import core.Window;

public class Telemetry {

	private static boolean telemetryChanged = false;
	private static int telemetryCount = 0;
	private static Vector2f position;

	private static void checkTelemetryChange() {
		telemetryChanged = false;
		int c = telemetryCount();
		if (c != telemetryCount) {
			telemetryCount = c;
			telemetryChanged = true;
		}
	}

	private static int telemetryCount() {
		int count = 0;
		if (GameData.activeFPS) {
			count++;
		}
		if (GameData.activeCount) {
			count++;
		}
		return count;
	}

	public static void update() {
		if (position == null) {
			position = new Vector2f(Window.getWidth() - 200, 0);
		}
		checkTelemetryChange();
		if (GameData.activeCount) {
			GameData.renderCount = 0;
		}
		if (GameData.activeFPS) {
			if (!FPS.isSetup) {
				FPS.setup();
			}
			FPS.updateFPS();
			GameData.fps = FPS.getFPS();
		}
	}

	public static void render() {
		Renderer.beginDraw(GL11.GL_QUADS);
		Renderer.drawQuad(position.x, position.y, 200, telemetryCount * 18, new Color(0, 0, 0, 0.5f));
		Renderer.endDraw();

		if (GameData.activeFPS) {
			Renderer.drawText(position.x, position.y, "Frames Per Second: " + GameData.fps, 16, Color.white);
		}
		if (GameData.activeCount) {
			Renderer.drawText(position.x, position.y + 16, "Render Count: " + (GameData.renderCount + 1), 16,
					Color.white);
		}

	}

	public static void destroy() {

	}
}
