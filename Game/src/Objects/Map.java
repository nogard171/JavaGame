package Objects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import Objects.MapData;
import Objects.ObjectData;
import Objects.ObjectType;
import util.TextureHandler;

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
	int textureCount =0;
	public void Draw(Graphics g)
	{
		textureCount =0;
		for(int x=0;x<10;x++)
		{
			for(int y=0;y<10;y++)
			{
				String key = (x*32) +","+(y*32);
				if(mapTiles.containsKey(key))
				{
					Tile tile = mapTiles.get(key);
					if(tile.texture==null&&textureCount<10)
					{
						tile.texture = this.getTexture(tile.type);
						textureCount++;
					}
					tile.Draw(g);
				}
			}
		}
	}

	public BufferedImage getTexture(ObjectType objType) {
		String location = "resources/images/grass.png";
		switch (objType) {
		case GRASS:
			location = "resources/images/grass.png";
			break;
		case DIRT:
			location = "resources/images/dirt.png";
			break;
		case PLAYER:
			location = "resources/images/player.png";
			break;
		}
		try {
			File file = new File(location);
			return ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

}
