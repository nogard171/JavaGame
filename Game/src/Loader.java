import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Loader
{
	public static Quad loadQuad()
	{
		Quad quad = new Quad();
		
		int dlid = GL11.glGenLists(1);	
		
		GL11.glNewList(dlid, GL11.GL_COMPILE);
		
		preRenderRawQuad(new RawQuad());
		
		GL11.glEndList();
		
		quad.setDisplayList(dlid);		
		
		return quad;
	}
	
	public static void preRenderRawQuad(RawQuad raw)
	{
		for(byte indice:raw.getIndices())
		{
			Vector2f vertice = raw.getVerticeFromIndice(indice);
			GL11.glVertex2f(vertice.getX(),vertice.getY());
		}
	}
}
