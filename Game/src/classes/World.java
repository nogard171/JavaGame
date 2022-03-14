package classes;

import java.awt.Point;
import java.util.LinkedList;

import data.EngineData;
import utils.Input;
import utils.View;
import utils.Window;

public class World {
	boolean mapMoved = true;
	private Point previousViewIndex = new Point(-1, -1);

	public void setup() {
		for (int x = 0; x < 10; x++) {
			for (int z = 0; z < 10; z++) {
				Chunk chunk = new Chunk(x, z);
				chunk.setup();
				EngineData.chunks.put(chunk.index.getString(), chunk);
			}
		}
	}

	static LinkedList<Object> objectsHovered = new LinkedList<Object>();

	private void optimizeChunkView(Point viewIndex) {
		EngineData.renderedChunks.clear();
		for (int x = viewIndex.x - 2; x < viewIndex.x + 3; x++) {
			for (int y = viewIndex.y - 2; y < viewIndex.y + 3; y++) {
				Chunk chunk = EngineData.chunks.get(x + "," + y);
				if (chunk != null) {
					EngineData.renderedChunks.add(chunk);
				}
			}
		}
	}

	public void update() {
		objectsHovered.clear();
		int cartX = (int) (Input.getMousePosition().x - View.x);
		int cartY = (int) (Input.getMousePosition().y - View.y);
		for (Chunk chunk : EngineData.renderedChunks) {
			if (chunk.bounds.contains(new Point(cartX, cartY))) {
				objectsHovered.addAll(chunk.getHoveredObjects());
			}
			chunk.update();
		}
	}

	public void render(Point viewIndex) {
		this.mapMoved = false;
		if (!previousViewIndex.equals(viewIndex)) {
			previousViewIndex = viewIndex;
			this.mapMoved = true;
		}
		if (this.mapMoved) {
			this.optimizeChunkView(viewIndex);
		}
		if (viewIndex != null) {
			// buildPathFindingMap(viewIndex);
			for (Chunk chunk : EngineData.renderedChunks) {
				if (chunk != null) {
					chunk.render();
				}

			}
		}
	}

	public void buildPathFindingMap(Point viewIndex) {
		int max = 5 * 16;
		EngineData.globalMapData = new int[max][max];
		for (int x = viewIndex.x - 2; x < viewIndex.x + 3; x++) {
			for (int y = viewIndex.y - 2; y < viewIndex.y + 3; y++) {
				Chunk chunk = EngineData.chunks.get(x + "," + y);
				if (chunk != null) {
					EngineData.globalMapData[x + 2][y + 2] = 0;
				}
			}
		}
	}

	public void destroy() {

	}

	public void refresh() {
		for (Chunk chunk : EngineData.chunks.values()) {
			chunk.refresh();
		}
	}

	public static LinkedList<Object> getHoveredObjects() {
		return objectsHovered;
	}

	public static Object getObjectAtIndex(Index tempIndex) {
		Object obj = null;

		int chunkX = (int) Math.floor(tempIndex.getX() / EngineData.chunkSize.getWidth());
		int chunkY = (int) Math.floor(tempIndex.getY() / EngineData.chunkSize.getDepth());

		int objX = (int) Math.floor(tempIndex.getX() % EngineData.chunkSize.getWidth());
		int objY = (int) Math.floor(tempIndex.getY() % EngineData.chunkSize.getDepth());

		Chunk chunk = EngineData.chunks.get(chunkX + "," + chunkY);
		if (chunk != null) {
			if (objX >= 0 && objY >= 0) {
				Object tempObj = chunk.objects[objX][objY];
				if (tempObj != null) {
					obj = tempObj;
				} else {
					tempObj = chunk.ground[objX][objY];
					if (tempObj != null) {
						obj = tempObj;
					}
				}
			}
		}
		return obj;
	}

	public static void rebuild() {
		for (Chunk chunk : EngineData.renderedChunks) {
			chunk.refresh();
		}
	}
}
