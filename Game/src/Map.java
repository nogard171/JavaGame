import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Objects.MapData;
import Objects.ObjectData;
import Objects.ObjectType;

public class Map extends MapData {
	HashMap<String, Tile> objects = new HashMap<String, Tile>();

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

	public Texture loadTexture(String texture_url) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(texture_url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texture;
	}

	public void addTile(ObjectData tileData) {
		Tile tile = new Tile(tileData.x, tileData.y, tileData.width, tileData.height);
		tile.type = tileData.type;
		// tiles.add(tile);
		objects.put(tileData.x + "," + tileData.y, tile);
	}

	
	
	public void Render(int playerX, int playerY) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glPushMatrix();
		GL11.glColor3f(1, 1, 1);
		int newWidth = Math.round((Display.getWidth() / 32)) + 3;
		int newHeight = Math.round((Display.getHeight() / 32)) + 3;
		int count = 0;
		int texturedCount = 0;
		int newX = (playerX / 32)-1;
		int newY = (playerY / 32)-1;
		if (objects.size() > 0) {
			for (int x = newX; x < (newX + newWidth); x++) {
				for (int y = newY; y < newY + newHeight; y++) {
					String key = (x * 32) + "," + (y * 32);
					if (objects.containsKey(key)) {
						if (objects.get(key) != null) {
							if (objects.get(key).texture == null&&texturedCount<=(newWidth+newHeight)/2) {
								objects.get(key).texture = getTexture(objects.get(key).type);
								texturedCount++;
							}
							objects.get(key).Render();
							count++;
						}
					}
				}
			}
		}
		System.out.println("Count:" + count);
		GL11.glPopMatrix();
		// GL11.glColor3f(0,0,0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public Texture getTexture(ObjectType objType) {
		String textureURL = "";
		switch (objType) {
		case GRASS:
			textureURL = "resources/images/grass.png";
			break;
		case DIRT:
			textureURL = "resources/images/dirt.png";
			break;
		default:
			break;
		}
		return loadTexture(textureURL);
	}
}
