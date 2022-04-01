package threads;

import utils.Input;
import utils.Ticker;

public class BaseThread extends Thread {
	private Ticker ticker;

	private static boolean isRunning = true;

	public void run() {
		this.setup();
		while (isRunning) {
			this.ticker.update(1000);
			this.update(this.ticker);
			this.update();
			this.render();
		}
		this.clean();
	}

	public void setup() {
		this.ticker = new Ticker();
	}

	public void update(Ticker ticker) {
	}

	public void update() {

	}

	public void render() {

	}

	public void clean() {

	}

	public static void close() {
		isRunning = false;
	}
}
