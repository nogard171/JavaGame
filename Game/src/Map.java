import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Objects.MapData;
import Objects.TileData;

public class Map extends MapData {
	HashMap<String, Tile> tiles = new HashMap<String, Tile>();

	public Map(ArrayList<TileData> ground) {
		this.ground = ground;
	}

	public Map() {
		// TODO Auto-generated constructor stub
	}

	public Texture loadTexture(String texture_url) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(texture_url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texture;
	}

	public void addTile(TileData tileData) {
		this.ground.add(tileData);

		Tile tile = new Tile(this.ground.size(),tileData.x, tileData.y, tileData.width, tileData.height);
		// tiles.add(tile);
		tiles.put(tileData.x + "," + tileData.y, tile);
	}

	public void Render() {
		GL11.glPushMatrix();
		GL11.glColor3f(1, 1, 1);
		for (int x = 0; x < Display.getWidth()/32; x++) {
			for (int y = 0; y < Display.getHeight()/32; y++) {
				String key = (x*32)+","+(y*32);
				if (tiles.get(key).texture == null) {
					Texture tex = loadTexture(this.ground.get(tiles.get(key).index).texture);
					tiles.get(key).texture = tex;
				}
				tiles.get(key).Render();
			}
		}
		GL11.glPopMatrix();
	}
}
