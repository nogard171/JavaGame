import static org.lwjgl.opengl.GL11.glCallList;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Renderer
{
	
	ShaderProgram shader = new ShaderProgram();
	private HashMap<String, Quad> quads = new HashMap<String, Quad>();
	// private ArrayList<Entity> entities = new ArrayList<Entity>();
	// private ArrayList<Entity> entitiesToDisplay = new ArrayList<Entity>();
	private HashMap<String, Entity> entities = new HashMap<String, Entity>();

	int renderWidth = 0;
	int renderHeight = 0;

	public void Init()
	{
		this.renderWidth = (int) (Display.getWidth() / 32);
		this.renderHeight = (int) (Display.getHeight() / 32);
		
		shader.loadFragmentShader("screen");
    	shader.loadVertexShader("screen");
    	shader.createProgram();

		new Loader();
		quads.put("GRASS", Loader.GenerateQuadWithTexture("grass.png"));
		quads.put("DIRT", Loader.GenerateQuadWithTexture("dirt.png"));
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
	}

	int count = 0;

	public void Render(View view)
	{
		shader.Start();

		int viewX =(int) Math.floor(-view.getPosition().getX() / 32);
		int viewY = (int)Math.floor(-view.getPosition().getY() / 32);
		int viewWidth =(int)Math.ceil(-view.getPosition().getX() / 32)+view.getViewWidth();
		int viewHeight =(int)Math.ceil(-view.getPosition().getY() / 32)+view.getViewHeight();
		count = 0;
		GL11.glPushMatrix();
		GL11.glTranslatef(view.getPosition().getX(), view.getPosition().getY(), 0);
		for (int x = viewX; x < viewWidth; x++)
		{
			for (int y = viewY; y < viewHeight; y++)
			{
				String key = x + "," + y;
				if (entities.containsKey(key))
				{
					Entity entity = entities.get(key);
					Quad quad = quads.get(entity.getQuadName());
					
					//GL11.glBindTexture(0, quad.getTextureID());
					shader.sendTexture( quad.getTextureID());
					
					GL11.glPushMatrix();
					GL11.glTranslatef(entity.getPosition().getX(), entity.getPosition().getY(), 0);
					GL11.glRotatef(entity.getRotation(), 0, 0, 1);
					GL11.glTranslatef(-entity.getOrigin().getX() * entity.getScale().getX(),
							-entity.getOrigin().getY() * entity.getScale().getY(), 0);
					GL11.glScalef(entity.getScale().getX(), entity.getScale().getY(), 0);
					GL11.glBegin(quad.getRenderType());
					glCallList(quad.getDisplayID());
					GL11.glEnd();
					GL11.glPopMatrix();
					GL11.glBindTexture(0, -1);
					count++;
				}
			}
		}
		GL11.glPopMatrix();
		System.out.println("Count:" + count);
	}

	public void Update()
	{

	}

	public void AssessEntities()
	{

	}

	public void addEntity(Entity entity)
	{
		String key = (int) (entity.getPosition().getX() / 32) + "," + (int) (entity.getPosition().getY() / 32);
		entities.put(key, entity);
	}

	public void Destroy()
	{
		for (Quad quad : quads.values())
		{
			GL11.glDeleteLists(quad.getDisplayID(), GL11.GL_COMPILE);
		}
	}
}
