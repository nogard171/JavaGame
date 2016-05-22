package Objects;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Objects.MapData;
import Objects.ObjectData;
import Objects.ObjectType;

public class Map extends MapData {
	HashMap<String, Tile> mapTiles = new HashMap<String, Tile>();
	HashMap<String, Tile> mapObjects = new HashMap<String, Tile>();

	public Map(ArrayList<ObjectData> ground) {
		this.ground = ground;
	}

	public Map(HashMap<String, ObjectData> tiles) {
		this.tiles = tiles;
	}

	public Map() {
		// TODO Auto-generated constructor stub
	}

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void addTile(ObjectData tileData) {
		Tile tile = new Tile(tileData.x, tileData.y, tileData.width, tileData.height);
		tile.type = tileData.type;
		tile.topType = tileData.topType;
		mapTiles.put(tileData.x + "," + tileData.y, tile);
	}

	public void addObject(ObjectData objectData) {
		// TODO Auto-generated method stub
		Tile tile = new Tile(objectData.x, objectData.y, objectData.width, objectData.height);
		tile.type = objectData.type;
		tile.topType = objectData.topType;
		mapObjects.put(objectData.x + "," + objectData.y, tile);
	}

	public void Render(int playerX, int playerY) {
		/*GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glPushMatrix();
		GL11.glColor3f(1, 1, 1);
		int newWidth = Math.round((Display.getWidth() / 32)) + 3;
		int newHeight = Math.round((Display.getHeight() / 32)) + 3;
		int count = 0;
		int texturedCount = 0;
		int newX = (playerX / 32) - 1;
		int newY = (playerY / 32) - 1;
		if (mapTiles.size() > 0) {
			for (int x = newX; x < (newX + newWidth); x++) {
				for (int y = newY; y < newY + newHeight; y++) {
					String key = (x * 32) + "," + (y * 32);
					if (mapTiles.containsKey(key)) {
						if (mapTiles.get(key) != null) {
							if (mapTiles.get(key).type != ObjectType.OTHER && mapTiles.get(key).texture == null
									&& texturedCount <= (newWidth + newHeight) / 2) {
								mapTiles.get(key).texture = getTexture(mapTiles.get(key).type);
								texturedCount++;
							}
							mapTiles.get(key).Render();
							count++;
						}
					}
					if (mapObjects.containsKey(key)) {
						if (mapObjects.get(key) != null) {
							if ( mapObjects.get(key).type != ObjectType.OTHER
									&& mapObjects.get(key).texture == null
									&& texturedCount <= (newWidth + newHeight) / 2) {
								
								mapObjects.get(key).texture = getTexture(mapObjects.get(key).type);
								
								texturedCount++;
							}
							if ( mapObjects.get(key).topType != ObjectType.OTHER
									&& mapObjects.get(key).topTexture == null
									&& texturedCount <= (newWidth + newHeight) / 2) {
								mapObjects.get(key).topTexture = getTexture(mapObjects.get(key).topType);
								texturedCount++;
							}
							mapObjects.get(key).Render();
							count++;
						}
					}
				}
			}
		}
		GL11.glPopMatrix();
		// GL11.glColor3f(0,0,0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);*/
	}

	public Color getColor(ObjectType objType) {
		Color color = Color.white;
		switch (objType) {
		case GRASS:
			color = Color.green;
			break;
		case DIRT:
			color = new Color(139, 69, 19);
			break;
		default:
			break;
		}
		return color;
	}

}
