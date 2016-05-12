package Game;

import org.lwjgl.opengl.Display;

public class Main
{
   
    public static void main(String[] args)
    {
	DisplayManager.createDisplay();
	
	 Loader loader = new Loader();
	 Renderer renderer = new Renderer();
	    
	 float[] vertices = {
		 //left bottom triangle
		 -0.5f,0.5f,0,
		 -0.5f,-0.5f,0,
		 0.5f,-0.5f,0,
		 0.5f,0.5f,0
		 };
	 int[] indices = {
		 0,1,3,
		 3,1,2
	 };
	 
	 Model model = loader.loadToVAO(vertices,indices);
	 
	 
	while(!Display.isCloseRequested())
	{
	    renderer.prepare();
	    //input
	    //update
	    renderer.render(model);
	    DisplayManager.updateDisplay();
	}
	loader.cleanUp();
	DisplayManager.closeDisplay();
    }
}
