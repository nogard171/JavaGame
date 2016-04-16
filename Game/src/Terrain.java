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
    int		     width    = 100;
    int		     height   = 100;
    int		  x	 = 0;
    int		  y	 = 0;
    boolean	     grid      = false;

    int		     tooLow    = -1000;
    
    ArrayList<Vector3f>	  map	 = new ArrayList<Vector3f>();
    Color4f[][]	  mapColour;

    float	  size	 = 1;

    public void render(int mode)
    {
	GL11.glPushMatrix();
	GL11.glTranslatef(x, 0, y);
	  
	
	
	GL11.glEnd();
	GL11.glPopMatrix();

    }

}
