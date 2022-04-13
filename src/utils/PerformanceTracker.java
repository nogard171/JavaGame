package utils;

import data.EngineData;

public class PerformanceTracker {
	static long startUpdate = 0;
	static long startRender = 0;
	static long startDestroy = 0;
	static long totalRender = 1;
	static long countRender = 1;
	static long totalUpdate = 1;
	static long countUpdate = 1;

	public static void update() {
		startUpdate = System.nanoTime();
		if (countRender > 10000) {
			totalRender = 1;
			countRender = 1;
		}
		if (startRender > 0) {
			totalRender += ((startUpdate - startRender) / 1000);
			countRender++;
			EngineData.timings.put("render", (totalRender / countRender));
		}
	}

	public static void render() {
		startRender = System.nanoTime();
		if (countUpdate > 10000) {
			totalUpdate = 1;
			countUpdate = 1;
		}
		if (startUpdate > 0) {
			totalUpdate += ((startRender - startUpdate) / 1000);
			countUpdate++;
			EngineData.timings.put("update", (totalUpdate / countUpdate));
		}
	}
}
