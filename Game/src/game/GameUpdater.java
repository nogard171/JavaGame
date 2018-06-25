package game;

import engine.FPSEngine;

public class GameUpdater {
	public void update() {
		System.out.println("FPS:" + FPSEngine.getFPS());
	}
}
