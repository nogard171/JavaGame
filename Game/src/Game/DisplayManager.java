package Game;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager
{
    static int	width	= 800;
    static int	height	= 600;
    int	far	= 1000;
    int	FOV	= 60;
    static int	FPS_cap	= 120;
    
    
    

    public static void createDisplay()
    {
	ContextAttribs attribs = new ContextAttribs(3,2);
	attribs.withForwardCompatible(true);
	attribs.withProfileCore(true);
	
	try{
	    Display.setDisplayMode(new DisplayMode(width,height));
	    Display.create(new PixelFormat(),attribs);
	}
	catch(LWJGLException e)
	{
	    e.printStackTrace();
	}    
	GL11.glViewport(0, 0, width, height);
    }
    public static void updateDisplay()
    {
	Display.sync(FPS_cap);
	Display.update();
    }
    public static void closeDisplay()
    {
	Display.destroy();
    }
}
