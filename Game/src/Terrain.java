import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.TrueTypeFont;

import com.sun.scenario.effect.Color4f;

public class Terrain
{
    int		     widthM    = 100;
    int		     heightM   = 100;
    // In this array the drawing will be made and stored
    ArrayList<Chunk> map       = new ArrayList<Chunk>();
    Color4f[][]	     mapColour = new Color4f[widthM][heightM];
    // use r.nextInt() in the code for random numbers
    Random	     r	       = new Random();
    boolean	     grid      = false;

    int		     tooLow    = -1000;

    public Terrain()
    {
	Chunk chunk = new Chunk();
	// chunk.width = widthM;
	// chunk.height = heightM;
	if (chunk.map == null)
	{
	    for (int y = 0; y < chunk.height; y++)
	    {
		for (int x = 0; x < chunk.width; x++)
		{
		    chunk.map[x][y] = 0;
		}
	    }
	}
	map.add(chunk);
    }

    public float randInt(float min, float max)
    {

	// Usually this can be a field rather than a method variable
	Random rand = new Random();

	// nextInt is normally exclusive of the top value,
	// so add 1 to make it inclusive
	float randomNum = (rand.nextInt((int) ((max * 10 - min * 10) + 1))
		+ min * 10) / 10;

	return randomNum;
    }

    Color4f tileHeight = new Color4f(1, 0, 0, 1);
    int	    prex       = 0, prez = 0;

    public float checkTile(float x, float z)
    {
	float returnHeight = 0;
	x = ((-x) + 0.5f);
	z = (-z) + 10f;
	// System.out.println("["+x+"]["+z+"]");
	for (int i = 0; i < map.size(); i++)
	{
	    if (map.get(i).map.length > x && x > 0
		    && map.get(i).map[0].length > z && z > 0)
	    {
		map.get(i).mapColour[prex][prez] = tileHeight;
		tileHeight = map.get(i).mapColour[(int) x][(int) z];
		returnHeight = map.get(i).map[(int) x][(int) z];
		map.get(i).mapColour[(int) x][(int) z] = new Color4f(1, 0, 0,
			1);
		prex = (int) x;
		prez = (int) z;
	    }
	}
	return returnHeight;
    }

    public boolean checkTileExists(float x, float z)
    {
	boolean returnExists = false;
	x = ((-x) + 0.5f);
	z = (-z) + 10f;
	// System.out.println("["+x+"]["+z+"]");
	for (int i = 0; i < map.size(); i++)
	{
	    if (map.get(i).map.length > x && x > 0
		    && map.get(i).map[0].length > z && z > 0)
	    {
		returnExists = true;
		break;
	    }
	}
	return returnExists;
    }

    public void Render(int mode, Rectangle bounds)
    {
	GL11.glDisable(GL11.GL_TEXTURE_2D);
	for (int i = 0; i < map.size(); i++)
	{
	    map.get(i).render(mode);
	}
	GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
