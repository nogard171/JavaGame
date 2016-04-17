import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;


public class Chunk
{
    int		  tooLow = -1000;
    int		  width	 = 100;
    int		  height = 100;
    int		  x	 = 0;
    int		  y	 = 0;
    int[][]	  map	 = null;
    Color[][]	  mapColour;

    float	  size	 = 1;
    public String title	 = "";

    public Chunk()
    {
	domidpoint();
    }

    public void checkSides(int index)
    {
	for (int y = 0; y < height; y++)
	{
	    for (int x = 0; x < width; x++)
	    {

	    }
	}
    }

    public void render(int mode)
    {
	GL11.glPushMatrix();
	GL11.glTranslatef(x, 0, y);
	for (int y = 0; y < height; y++)
	{
	    for (int x = 0; x < width; x++)
	    {
		if (mapColour[x][y] == null)
		{
		    float gradiant = 0;
		    Color color = new Color(0, 0, 0, 0);
		    if (map[x][y] <= 50 && map[x][y] >= tooLow)
		    {
			float plus = 0.5f;
			gradiant = ((float) 1f / ((-map[x][y]) + 6));
			color = new Color((gradiant / 2) + plus,
				(gradiant / 2) + plus,
				(gradiant / 4) + plus / 2, 1f);
		    }
		    else if (map[x][y] > 50 && map[x][y] < 100)
		    {
			gradiant = ((float) 1f / (map[x][y]));
			color = new Color(0, gradiant + 0.2f, 0, 1);
		    }
		    else if (map[x][y] >= 100 && map[x][y] <= 150)
		    {
			gradiant = 1f - ((float) 1f / ((map[x][y] - 20) / 12));
			color = new Color(0.6f + gradiant, 0.6f + gradiant,
				0.6f + gradiant, 1);
		    }
		    else
		    {
			color = new Color(1, 1, 1, 1);
		    }

		    GL11.glColor4f(color.getRed(), color.getGreen(),
			    color.getBlue(), color.getAlpha());
		}
		else
		{
		    GL11.glColor4f(mapColour[x][y].getRed(),
			    mapColour[x][y].getGreen(),
			    mapColour[x][y].getBlue(),
			    mapColour[x][y].getAlpha());
		}
		GL11.glBegin(mode);
		// top
		float heightT = map[x][y];
		if (x > 0 && x + 1 < width && y > 0 && y + 1 < height)
		{
		    float height1, height2, height3, height4, height5, height6,
			    height7, height8, height9;
		    if (map[x + 1][y] < tooLow)
		    {
			height1 = tooLow;
		    }
		    else
		    {
			height1 = map[x + 1][y];
		    }
		    if (map[x + 1][y + 1] < tooLow)
		    {
			height2 = tooLow;
		    }
		    else
		    {
			height2 = map[x + 1][y + 1];
		    }
		    if (map[x][y] < tooLow)
		    {
			height3 = tooLow;
		    }
		    else
		    {
			height3 = map[x][y];
		    }
		    if (map[x][y + 1] < tooLow)
		    {
			height4 = tooLow;
		    }
		    else
		    {
			height4 = map[x][y + 1];
		    }
		    if (map[x][y - 1] < tooLow)
		    {
			height5 = tooLow;
		    }
		    else
		    {
			height5 = map[x][y - 1];
		    }
		    if (map[x + 1][y - 1] < tooLow)
		    {
			height6 = tooLow;
		    }
		    else
		    {
			height6 = map[x + 1][y - 1];
		    }
		    if (map[x - 1][y] < tooLow)
		    {
			height7 = tooLow;
		    }
		    else
		    {
			height7 = map[x - 1][y];
		    }
		    if (map[x - 1][y + 1] < tooLow)
		    {
			height8 = tooLow;
		    }
		    else
		    {
			height8 = map[x - 1][y + 1];
		    }
		    if (map[x - 1][y - 1] < tooLow)
		    {
			height9 = tooLow;
		    }
		    else
		    {
			height9 = map[x - 1][y - 1];
		    }

		    float mid = (height1 + height2 + height3 + height4) / 4;
		    float mid2 = (height5 + height3 + height6 + height1) / 4;
		    float mid3 = (height7 + height8 + height3 + height4) / 4;
		    float mid4 = (height7 + height9 + height3 + height5) / 4;
		    // top
		    GL11.glVertex3f((x), mid3, (y) + 1);
		    GL11.glVertex3f((x) + 1, mid, (y) + 1);
		    GL11.glVertex3f((x) + 1, mid2, (y));
		    GL11.glVertex3f(x, mid4, y);

		}
		else if (x + 1 < width && y > 0 && y + 1 < height)
		{
		    float height1, height2, height3, height4, height5, height6,
			    height7, height8, height9;
		    if (map[x + 1][y] < tooLow)
		    {
			height1 = tooLow;
		    }
		    else
		    {
			height1 = map[x + 1][y];
		    }
		    if (map[x + 1][y + 1] < tooLow)
		    {
			height2 = tooLow;
		    }
		    else
		    {
			height2 = map[x + 1][y + 1];
		    }
		    if (map[x][y] < tooLow)
		    {
			height3 = tooLow;
		    }
		    else
		    {
			height3 = map[x][y];
		    }
		    if (map[x][y + 1] < tooLow)
		    {
			height4 = tooLow;
		    }
		    else
		    {
			height4 = map[x][y + 1];
		    }
		    if (map[x][y - 1] < tooLow)
		    {
			height5 = tooLow;
		    }
		    else
		    {
			height5 = map[x][y - 1];
		    }
		    if (map[x + 1][y - 1] < tooLow)
		    {
			height6 = tooLow;
		    }
		    else
		    {
			height6 = map[x + 1][y - 1];
		    }

		    float mid = (height1 + height2 + height3 + height4) / 4;
		    float mid2 = (height5 + height3 + height6 + height1) / 4;
		    float mid3 = (height3 + height4) / 2;
		    float mid4 = (height3 + height5) / 2;
		    // top
		    GL11.glVertex3f((x), mid3, (y) + 1);
		    GL11.glVertex3f((x) + 1, mid, (y) + 1);
		    GL11.glVertex3f((x) + 1, mid2, (y));
		    GL11.glVertex3f(x, mid4, y);

		}
		else
		{

		    GL11.glColor4f(1, 0, 0, 1);
		    // top
		    GL11.glVertex3f((x), heightT, (y) + 1);
		    GL11.glVertex3f((x) + 1, heightT, (y) + 1);
		    GL11.glVertex3f((x) + 1, heightT, (y));
		    GL11.glVertex3f(x, heightT, y);
		}
		GL11.glEnd();

	    }
	}

	GL11.glBegin(mode);
	for (int y = 0; y < height; y++)
	{

	    for (int x = 0; x < width; x++)
	    {
		if (map[x][y] < 1)
		{

		    float gradiant = 0;
		    Color color = new Color(0, 0, 0, 0);
		    if (map[x][y] < 5)
		    {
			gradiant = ((float) 1f / ((-map[x][y])));
			color = new Color(0, 0, 0.3f + gradiant, 0.5f);
		    }
		    GL11.glColor4f(color.getRed(), color.getGreen(),
			    color.getBlue(), color.getAlpha());
		    // top
		    GL11.glVertex3f((x), -1, (y) + 1);
		    GL11.glVertex3f((x) + 1, -1, (y) + 1);
		    GL11.glVertex3f((x) + 1, -1, (y));
		    GL11.glVertex3f(x, -1, y);
		}

	    }
	}
	GL11.glEnd();
	GL11.glPopMatrix();

    }

    Random r = new Random();

    // Here the midpoint code begins.
    public void domidpoint()
    {
	map = new int[width][height];
	mapColour = new Color[width][height];
	// Erase the old map array..
	for (int y = 0; y < height; y++)
	{
	    for (int x = 0; x < width; x++)
	    {
		map[x][y] = 0;
	    }
	}
	// Setup points in the 4 corners of the map.
	map[0][0] = 12;
	map[width - 1][0] = 128;
	map[width - 1][height - 1] = 128;
	map[0][height - 1] = 128;
	// Do the midpoint
	//midpoint(0, 0, width - 1, height - 1);
    }

    // This is the actual mid point displacement code.
    public boolean midpoint(int x1, int y1, int x2, int y2)
    {
	// If this is pointing at just on pixel, Exit because
	// it doesn't need doing}
	if (x2 - x1 < 2 && y2 - y1 < 2)
	    return false;

	// Find distance between points and
	// use when generating a random number.
	int dist = (x2 - x1 + y2 - y1);
	int hdist = dist / 2;
	// Find Middle Point
	int midx = (x1 + x2) / 2;
	int midy = (y1 + y2) / 2;
	// Get pixel colors of corners
	int c1 = map[x1][y1];
	int c2 = map[x2][y1];
	int c3 = map[x2][y2];
	int c4 = map[x1][y2];

	// If Not already defined, work out the midpoints of the corners of
	// the rectangle by means of an average plus a random number.
	if (map[midx][y1] == 0)
	    map[midx][y1] = ((c1 + c2 + r.nextInt(dist) - hdist) / 2);
	if (map[midx][y2] == 0)
	    map[midx][y2] = ((c4 + c3 + r.nextInt(dist) - hdist) / 2);
	if (map[x1][midy] == 0)
	    map[x1][midy] = ((c1 + c4 + r.nextInt(dist) - hdist) / 2);
	if (map[x2][midy] == 0)
	    map[x2][midy] = ((c2 + c3 + r.nextInt(dist) - hdist) / 2);

	// Work out the middle point...
	map[midx][midy] = ((c1 + c2 + c3 + c4 + r.nextInt(dist) - hdist) / 4);

	// Now divide this rectangle into 4, And call again For Each smaller
	// rectangle
	midpoint(x1, y1, midx, midy);
	midpoint(midx, y1, x2, midy);
	midpoint(x1, midy, midx, y2);
	midpoint(midx, midy, x2, y2);

	return true;
    }
}
