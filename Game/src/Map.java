import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import Objects.MapData;
import Objects.ObjectData;
import Objects.ObjectType;

public class Map extends MapData
{
    HashMap<String, Tile>   mapTiles   = new HashMap<String, Tile>();
    HashMap<String, Entity> mapObjects = new HashMap<String, Entity>();

    public Map(ArrayList<ObjectData> ground)
    {
	this.ground = ground;
    }

    public Map(HashMap<String, ObjectData> tiles)
    {
	this.tiles = tiles;
    }

    public Map()
    {
	// TODO Auto-generated constructor stub
    }

    public Map(int width, int height)
    {
	this.width = width;
	this.height = height;
    }

    public Texture loadTexture(String texture_url)
    {
	Texture texture = null;
	try
	{
	    texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(texture_url));
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	}
	return texture;
    }

    public void addTile(ObjectData tileData)
    {
	Tile tile = new Tile(tileData.x, tileData.y, tileData.width, tileData.height);
	tile.depth = tileData.depth;
	tile.type = tileData.type;
	tile.topType = tileData.topType;
	mapTiles.put(tileData.x + "," + tileData.y, tile);
    }

    public void addObject(ObjectData objectData)
    {
	// TODO Auto-generated method stub
	Entity tile = new Entity(objectData.x, objectData.y, objectData.width, objectData.height);
	tile.type = objectData.type;
	tile.topType = objectData.topType;
	mapObjects.put(objectData.x + "," + objectData.y, tile);
    }

    public Entity getEntity(int x, int y)
    {
	String key = x + "," + y;
	if (mapObjects.containsKey(key))
	{
	    return mapObjects.get(key);
	}
	return null;
    }

    public void RenderTop(int playerX, int playerY)
    {
	int newWidth = Math.round((Display.getWidth() / 32)) + 3;
	int newHeight = Math.round((Display.getHeight() / 32)) + 3;
	int count = 0;
	int texturedCount = 0;
	int newX = (playerX / 32) - 1;
	int newY = (playerY / 32) - 1;
	if (mapObjects.size() > 0)
	{
	    for (int y = newY; y < newY + newHeight; y++)
	    {
		for (int x = newX; x < (newX + newWidth); x++)
		{

		    String key = (x * 32) + "," + (y * 32);
		    if (mapObjects.containsKey(key))
		    {
			Entity object = mapObjects.get(key);
			if (object != null)
			{
			    if (object.topType != ObjectType.OTHER && object.topTexture == null
				    && texturedCount <= (newWidth + newHeight) / 2)
			    {
				object.topTexture = getTexture(object.topType);
				texturedCount++;
			    }
			    object.RenderTop();
			    count++;
			}
		    }
		}
	    }
	}
    }

    public void RenderBottom(int playerX, int playerY)
    {
	int newWidth = Math.round((Display.getWidth() / 32)) + 3;
	int newHeight = Math.round((Display.getHeight() / 32)) + 3;
	int count = 0;
	int texturedCount = 0;
	int newX = (playerX / 32) - 1;
	int newY = (playerY / 32) - 1;
	if (mapTiles.size() > 0)
	{
	    for (int x = newX; x < (newX + newWidth); x++)
	    {
		for (int y = newY; y < newY + newHeight; y++)
		{
		    String key = (x * 32) + "," + (y * 32);
		    if (mapTiles.containsKey(key))
		    {
			if (mapTiles.get(key) != null)
			{
			    if (mapTiles.get(key).type != ObjectType.OTHER && mapTiles.get(key).texture == null
				    && texturedCount <= (newWidth + newHeight) / 2)
			    {
				mapTiles.get(key).texture = getTexture(mapTiles.get(key).type);
				texturedCount++;
			    }
			    mapTiles.get(key).Render();
			    count++;
			}
		    }
		    if (mapObjects.containsKey(key))
		    {
			if (mapObjects.get(key) != null)
			{
			    if (mapObjects.get(key).type != ObjectType.OTHER && mapObjects.get(key).texture == null
				    && texturedCount <= (newWidth + newHeight) / 2)
			    {
				mapObjects.get(key).texture = getTexture(mapObjects.get(key).type);
				texturedCount++;
			    }
			    mapObjects.get(key).Render();
			    count++;
			}
		    }
		}
	    }
	}
    }

    public Texture getTexture(ObjectType objType)
    {
	String textureURL = "";
	switch (objType)
	{
	    case GRASS:
		textureURL = "resources/images/grass.png";
		break;
	    case DIRT:
		textureURL = "resources/images/dirt.png";
		break;
	    case TREE:
		textureURL = "resources/images/tree.png";
		break;
	    case TRUNK:
		textureURL = "resources/images/trunk.png";
		break;
	    case WATER:
		textureURL = "resources/images/water.png";
		break;
	    case DEEPWATER:
		textureURL = "resources/images/deepwater.png";
		break;
	    case SNOW:
		textureURL = "resources/images/snow.png";
		break;
	    case STONE:
		textureURL = "resources/images/stone.png";
		break;
	    case SAND:
		textureURL = "resources/images/sand.png";
		break;
	    default:
		break;
	}
	return loadTexture(textureURL);
    }

    public Color getColor(ObjectType objType)
    {
	Color color = Color.white;
	switch (objType)
	{
	    case GRASS:
		color = new Color(77, 134, 53);
		break;
	    case DIRT:
		color = new Color(139, 69, 19);
		break;
	    default:
		break;
	}
	return color;
    }

    public void colide(Entity test)
    {
	int playerY = test.y + test.screenPosition.y;
	int playerX = test.x + test.screenPosition.x;
	int newWidth = Math.round((Display.getWidth() / 32)) + 3;
	int newHeight = Math.round((Display.getHeight() / 32)) + 3;
	int count = 0;
	int texturedCount = 0;
	int newX = (playerX / 32) - 1;
	int newY = (playerY / 32) - 1;
	if (mapTiles.size() > 0)
	{
	    for (int x = newX - 2; x < (newX + 4); x++)
	    {
		for (int y = newY - 2; y < newY + 4; y++)
		{
		    String key = (x * 32) + "," + (y * 32);
		    if (mapObjects.containsKey(key))
		    {
			// System.out.println((test.x+test.screenPosition.x)+","+(test.y+test.screenPosition.y));
			if (mapObjects.get(key) != null
				&& new Rectangle(x * 32, y * 32, 32, 32)
					.intersects(new Rectangle(playerX, playerY + 32, 32, 32))
				&& test.collosionDir == Direction.NONE)
			{
			    System.out.println("true");
			    test.color = new org.lwjgl.util.Color(255, 0, 0);
			    // mapObjects.get(key).color= new
			    // org.lwjgl.util.Color(255,0,0);
			    test.collosionDir = test.isFacing;
			}
			else if (test.collosionDir == Direction.NONE)
			{
			    test.color = test.defaultColor;
			    // mapObjects.get(key).color=
			    // mapObjects.get(key).defaultColor;
			}
			else if (test.collosionDir != Direction.NONE)
			{
			    test.collosionDir = Direction.NONE;
			}
		    }
		}
	    }
	}

    }

}
