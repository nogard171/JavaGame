package classes;

import java.awt.Point;

import data.WorldData;

public class World {

	Point previousViewIndex;
	boolean mapMoved = true;

	public void setup() {
		for (int x = 0; x < 2; x++) {
			for (int z = 0; z < 2; z++) {
				Chunk chunk = new Chunk(x, z);
				chunk.setup();
				WorldData.chunks.put(chunk.index.getString(), chunk);
			}
		}
	}

	public void update() {
		for (Chunk chunk : WorldData.chunks.values()) {
			chunk.update();
		}
	}

	public void render(Point viewIndex) {
		if (viewIndex != null) {
			buildPathFindingMap(viewIndex);
			for (int x = viewIndex.x - 2; x < viewIndex.x + 3; x++) {
				for (int y = viewIndex.y - 2; y < viewIndex.y + 3; y++) {
					Chunk chunk = WorldData.chunks.get(x + "," + y);
					if (chunk != null) {
						chunk.render();
					}
				}
			}
		}
	}

	public void buildPathFindingMap(Point viewIndex) {
		int max = 5 * 16;
		WorldData.globalMapData = new int[max][max];

		for (int x = viewIndex.x - 2; x < viewIndex.x + 3; x++) {
			for (int y = viewIndex.y - 2; y < viewIndex.y + 3; y++) {
				Chunk chunk = WorldData.chunks.get(x + "," + y);
				if (chunk != null) {
					WorldData.globalMapData[x + 2][y + 2] = 0;
				}
			}
		}
	}

	public void destroy() {

	}

	public void refresh() {
		for (Chunk chunk : WorldData.chunks.values()) {
			chunk.refresh();
		}
	}
}
