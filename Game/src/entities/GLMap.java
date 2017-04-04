package entities;

import java.awt.Dimension;

public class GLMap
{
	GLObject[][][] data = new GLObject[0][0][0];
	private Dimension mapSize = new Dimension(10,10);

	public GLMap()
	{
		data = new GLObject[(int) mapSize.getWidth()][ (int) mapSize.getHeight()][1];
		for (int x = 0; x < mapSize.getWidth(); x++)
		{
			for (int y = 0; y < mapSize.getHeight(); y++)
			{
				GLObject object = new GLObject();
				object.setPosition(x*32, y*32);

				data[x][y][0] = object;
			}
		}
	}

	public GLObject[][][] getData()
	{
		return data;
	}

	public void setData(GLObject[][][] data)
	{
		this.data = data;
	}

	public Dimension getMapSize()
	{
		return mapSize;
	}

	public void setMapSize(Dimension mapSize)
	{
		this.mapSize = mapSize;
	}
}
