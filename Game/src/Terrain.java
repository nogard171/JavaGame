import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.TrueTypeFont;

public class Terrain {
	int width = 100;
	int height = 100;
	int x = 0;
	int y = 0;
	boolean grid = false;

	int tooLow = -1000;

	ArrayList<OBJ> objects = new ArrayList<OBJ>();
	int[][] map = new int[100][100];
	Color[][] mapColour;

	float size = 1;
	
	public Terrain() {		
		
		map = new int[width][height];
	}
	public int[] checkSides(OBJ obj)
	{
		int[] sides = {-1,-1,-1,-1,-1,-1};
		for (int x = 0; x < objects.size(); x++) 
		{
			if(objects.get(x).x==obj.x-2)
			{
				sides[0] = 0;
			}
			if(objects.get(x).x==obj.x+2)
			{
				sides[1] = 0;
			}
			if(objects.get(x).z==obj.z-2)
			{
				sides[2] = 0;
			}
			if(objects.get(x).z==obj.z+2)
			{
				sides[3] = 0;
			}
			if(objects.get(x).y==obj.y-2)
			{
				sides[4] = 0;
			}
			if(objects.get(x).y==obj.y+2)
			{
				sides[5] = 0;
			}
		}
		return sides;
	}
	public void render(Camera cam,int mode) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, 0, y);
		for (int x = 0; x < objects.size(); x++) 
		{
			int dist = (int) -((-cam.position.x-objects.get(x).x)+(-cam.position.z-objects.get(x).z));
			if(dist<5)
			{
			int[] sides = checkSides(objects.get(x));
			sides = checkCamera(sides, cam,objects.get(x));
			objects.get(x).renderOBJ(sides,mode);
			}
		}
		GL11.glEnd();
		GL11.glPopMatrix();

	}

	private int[] checkCamera(int[] sides, Camera cam, OBJ obj) {
		
		if(obj.y>-cam.position.y&&sides[5]<=-1)
		{
			sides[5] = 0;
		}	
		if(obj.y<-cam.position.y&&sides[4]<=-1)
		{
			sides[4] = 0;
		}	
		if(obj.x>cam.position.x&&sides[0]<=-1)
		{
			sides[0] = 0;
		}
		if(obj.x>-cam.position.x&&sides[1]<=-1)
		{
			sides[1] = 0;
		}		
		if(obj.z>cam.position.z&&sides[2]<=-1)
		{
			sides[2] = 0;
		}
		if(obj.z>-cam.position.z&&sides[3]<=-1)
		{
			sides[3] = 0;
		}
		return sides;
	}
	public void renderAxis() {

	}

}
