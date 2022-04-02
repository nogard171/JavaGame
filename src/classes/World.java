package classes;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Color;

import data.EngineData;
import data.Settings;
import utils.Input;
import utils.Renderer;
import utils.Window;

public class World {

	public LinkedList<Object> characters = new LinkedList<Object>();
	boolean mapMoved = true;
	private Point previousViewIndex = new Point(-1, -1);

	public void setup() {
		Object character0 = new Object();
		character0.setModel("CHARACTER");
		character0.setMaterial("CHARACTER");

		characters.add(character0);

		for (int x = 0; x < 10; x++) {
			for (int z = 0; z < 10; z++) {
				if (Settings.localChunks) {
					EngineData.loadChunks.add(new Index(x, z));
				} else {
					Chunk newChunk = new Chunk(x, z);
					newChunk.setup();
					if (x == 0 && z == 0) {
						newChunk.addCharacter(new Index(5, 5), character0);
					}
					EngineData.chunks.put(newChunk.index.getString(), newChunk);
				}
				System.out.println("Chunk:"+x+","+z);
			}
		}
	}

	static LinkedList<Object> objectsHovered = new LinkedList<Object>();

	private void optimizeChunkView(Point viewIndex) {
		EngineData.renderedChunks.clear();
		for (int x = viewIndex.x - 4; x < viewIndex.x + 5; x++) {
			for (int y = viewIndex.y - 4; y < viewIndex.y + 5; y++) {
				String key = x + "," + y;
				if (x >= 0 && y >= 0) {
					Chunk chunk = EngineData.chunks.get(key);
					if (chunk == null) {
						if (Settings.localChunks) {
							if ((x == (viewIndex.x - 3) || y == (viewIndex.y - 3) || x == (viewIndex.x + 3)
									|| y == (viewIndex.y + 3))) {
								EngineData.loadChunks.add(new Index(x, y));
							}
						} else if (Settings.infinate) {
							Chunk newChunk = new Chunk(x, y);
							newChunk.setup();
							EngineData.renderedChunks.add(newChunk);
							EngineData.chunks.put(newChunk.index.getString(), newChunk);
						}
					} else {
						if (Settings.localChunks) {
							if ((x == (viewIndex.x - 4) || y == (viewIndex.y - 4) || x == (viewIndex.x + 5)
									|| y == (viewIndex.y + 5))) {
								EngineData.cleanupChunks.add(chunk);
							}
						} else {
							EngineData.renderedChunks.add(chunk);
						}
					}

				}
			}
		}

	}

	public void update() {
		objectsHovered.clear();
		if (!EngineData.blockInput) {
			int cartX = (int) (Input.getMousePosition().x - View.getX());
			int cartY = (int) (Input.getMousePosition().y - View.getY());
			for (Chunk chunk : EngineData.renderedChunks) {
				if (chunk.bounds.contains(new Point(cartX, cartY))) {
					objectsHovered.addAll(chunk.getHoveredObjects());
					chunk.setBoundsColor(Color.green);
				} else {
					chunk.setBoundsColor(Color.red);
				}
				chunk.update();
			}
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
			for (Chunk chunk : EngineData.renderedChunks) {
				if (chunk != null) {
					chunk.render();
				}

			}
		}
	}

	public void clean() {

	}

	public void refresh() {
		for (Chunk chunk : EngineData.chunks.values()) {
			chunk.refresh();
			chunk.update();
			chunk.render();
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

	public static int isPassable(Index tempIndex) {
		int chunkX = (int) Math.floor(tempIndex.getX() / EngineData.chunkSize.getWidth());
		int chunkY = (int) Math.floor(tempIndex.getY() / EngineData.chunkSize.getDepth());

		int objX = (int) Math.floor(tempIndex.getX() % EngineData.chunkSize.getWidth());
		int objY = (int) Math.floor(tempIndex.getY() % EngineData.chunkSize.getDepth());

		Chunk chunk = EngineData.chunks.get(chunkX + "," + chunkY);
		if (chunk != null) {
			if (objX >= 0 && objY >= 0) {
				return (chunk.passable[objX][objY]?1:0);
			}
		}
		return -1;
	}
}
