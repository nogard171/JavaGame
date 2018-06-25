package engine;

import org.lwjgl.input.Keyboard;

import data.DataHub;

public class Updater {
	public static void update() {
		DataHub.input.poll();
	}
}
