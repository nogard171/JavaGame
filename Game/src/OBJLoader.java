import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class OBJLoader
{
    ArrayList<Vector3f>	vectors		= new ArrayList<Vector3f>();
    ArrayList<Vector4f>	faces		= new ArrayList<Vector4f>();
    ArrayList<Vector2f>	textureVectors	= new ArrayList<Vector2f>();
    String		textureLocation	= "";
    String		mtlFile		= "";
    String		objectName	= "";
    String		textureFile	= "";

    public void loadData(String file)
    {
	FileInputStream fis = null;
	try
	{
	    fis = new FileInputStream(file);

	}
	catch (FileNotFoundException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	// Construct BufferedReader from InputStreamReader
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));

	String line = null;
	try
	{
	    while ((line = br.readLine()) != null)
	    {
		// System.out.println(line);
		if (line.startsWith("mtllib "))
		{
		    String[] data = line.split(" ");
		    mtlFile = data[1];
		    textureFile = loadMTL(mtlFile);
		}
		if (line.startsWith("o "))
		{
		    String[] data = line.split(" ");
		    objectName = data[1];
		}
		if (line.startsWith("v "))
		{
		    String[] data = line.split(" ");
		    Vector3f vec;
		    if(data[1]=="")
		    {
			 vec = new Vector3f(Float.parseFloat(data[2]),
				    Float.parseFloat(data[3]),
				    Float.parseFloat(data[4]));
		    }
		    else
		    {
			vec = new Vector3f(Float.parseFloat(data[1]),
				    Float.parseFloat(data[2]),
				    Float.parseFloat(data[3]));
		    }
		   
		    vectors.add(vec);
		}
		if (line.startsWith("vt "))
		{
		    String[] data = line.split(" ");
		    Vector2f vec = new Vector2f(Float.parseFloat(data[1]),
			    Float.parseFloat(data[2]));
		    textureVectors.add(vec);
		}
		if (line.startsWith("f "))
		{
		    String[] data = line.split(" ");
		    Vector4f vec;
		    if (data.length > 4)
		    {
			vec = new Vector4f(
				Integer.parseInt(data[2].split("/")[0]),
				Integer.parseInt(data[3].split("/")[0]),
				Integer.parseInt(data[4].split("/")[0]),
				Integer.parseInt(data[1].split("/")[0]));
		    }
		    else
		    {
			vec = new Vector4f(
				Integer.parseInt(data[2].split("/")[0]),
				Integer.parseInt(data[3].split("/")[0]),
				Integer.parseInt(data[3].split("/")[0]),
				Integer.parseInt(data[1].split("/")[0]));
		    }
		    faces.add(vec);
		}

	    }
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	try
	{
	    br.close();
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public String loadMTL(String file)
    {
	FileInputStream fis = null;
	try
	{
	    fis = new FileInputStream(file);

	}
	catch (FileNotFoundException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	// Construct BufferedReader from InputStreamReader
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	String texture = "";
	String line = null;
	try
	{
	    while ((line = br.readLine()) != null)
	    {
		// System.out.println(line);
		if (line.startsWith("map_Kd "))
		{
		    String[] data = line.split(" ");
		    texture = data[1];
		}

	    }
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	try
	{
	    br.close();
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return texture;
    }

    float	 x   = 0;
    float	 y   = 0;
    float	 z   = 0;
    public float yaw = 0;

    public void drawOBJ(int terrainMode)
    {
	GL11.glDisable(GL11.GL_TEXTURE_2D);
	GL11.glPushMatrix();
	GL11.glTranslatef(x, y, z);
	GL11.glRotatef(yaw, 0.0f, 0.1f, 0.0f);
	int mode = 0;
	if (terrainMode == GL11.GL_QUADS)
	{
	    mode = GL11.GL_QUADS;
	}
	else
	{
	    mode = GL11.GL_LINE_STRIP;
	}
	for (int f = 0; f < faces.size(); f++)
	{

	    GL11.glColor4f(0.5f, 0.5f, 0.5f, 1);
	    GL11.glBegin(mode);
	    GL11.glVertex3f(vectors.get((int) faces.get(f).w - 1).x,
		    vectors.get((int) faces.get(f).w - 1).y,
		    vectors.get((int) faces.get(f).w - 1).z);
	    GL11.glVertex3f(vectors.get((int) faces.get(f).x - 1).x,
		    vectors.get((int) faces.get(f).x - 1).y,
		    vectors.get((int) faces.get(f).x - 1).z);
	    GL11.glVertex3f(vectors.get((int) faces.get(f).y - 1).x,
		    vectors.get((int) faces.get(f).y - 1).y,
		    vectors.get((int) faces.get(f).y - 1).z);
	    GL11.glVertex3f(vectors.get((int) faces.get(f).z - 1).x,
		    vectors.get((int) faces.get(f).z - 1).y,
		    vectors.get((int) faces.get(f).z - 1).z);
	    GL11.glEnd();

	}
	mode = GL11.GL_LINE_STRIP;
	for (int f = 0; f < faces.size(); f++)
	{

	    GL11.glColor4f(0f, 0f, 0f, 1);
	    GL11.glBegin(mode);
	    GL11.glVertex3f(vectors.get((int) faces.get(f).w - 1).x,
		    vectors.get((int) faces.get(f).w - 1).y,
		    vectors.get((int) faces.get(f).w - 1).z);
	    GL11.glVertex3f(vectors.get((int) faces.get(f).x - 1).x,
		    vectors.get((int) faces.get(f).x - 1).y,
		    vectors.get((int) faces.get(f).x - 1).z);
	    GL11.glVertex3f(vectors.get((int) faces.get(f).y - 1).x,
		    vectors.get((int) faces.get(f).y - 1).y,
		    vectors.get((int) faces.get(f).y - 1).z);
	    GL11.glVertex3f(vectors.get((int) faces.get(f).z - 1).x,
		    vectors.get((int) faces.get(f).z - 1).y,
		    vectors.get((int) faces.get(f).z - 1).z);
	    GL11.glEnd();

	}
	GL11.glPopMatrix();
	GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void drawLine(int mode, float x, float y)
    {
	GL11.glDisable(GL11.GL_TEXTURE_2D);
	GL11.glPushMatrix();
	GL11.glTranslatef(x, y, z);
	GL11.glRotatef(yaw, 0.0f, 0.1f, 0.0f);
	GL11.glColor4f(0.5f, 0.5f, 0.5f, 1);
	GL11.glBegin(mode);
	GL11.glVertex3f(x,
		y,
		x);
	GL11.glVertex3f(x,
		y+1f,
		x);
	GL11.glVertex3f(x+1f,
		y+1f,
		x);
	GL11.glVertex3f(x+1f,
		y,
		x);
	GL11.glEnd();
	GL11.glPopMatrix();
	GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
