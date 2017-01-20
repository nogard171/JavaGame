import static org.lwjgl.opengl.GL11.glCallList;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Renderer
{
	private HashMap<String, Quad> quads = new HashMap<String, Quad>();
	// private ArrayList<Entity> entities = new ArrayList<Entity>();
	// private ArrayList<Entity> entitiesToDisplay = new ArrayList<Entity>();
	private HashMap<String, Entity> entities = new HashMap<String, Entity>();
	
	int renderWidth = 0;
	int renderHeight = 0;

	public void Init()
	{
		this.renderWidth = (int)(Display.getWidth()/32);
		this.renderHeight = (int)(Display.getHeight()/32);
		
		
		new Loader();		
		quads.put("GRASS", Loader.GenerateQuadWithTexture("grass.png"));
	}
int count=0;
	public void Render(View view)
	{

		/*for (Entity entity : entitiesToDisplay)
		{
			Quad quad = quads.get(entity.getQuadName());
			GL11.glPushMatrix();
			GL11.glTranslatef(entity.getPosition().getX(), entity.getPosition().getY(), 0);
			GL11.glRotatef(entity.getRotation(), 0, 0, 1);
			GL11.glTranslatef(-entity.getOrigin().getX() * entity.getScale().getX(),
					-entity.getOrigin().getY() * entity.getScale().getY(), 0);
			GL11.glScalef(entity.getScale().getX(), entity.getScale().getY(), 0);
			GL11.glColor3f(1, 1, 1);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(0, quad.getTextureID());
			GL11.glBegin(quad.getRenderType());
			glCallList(quad.getDisplayID());
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
		}*/
		
		int viewX = (int)(-view.getPosition().getX()/32);
		int viewY = (int)(-view.getPosition().getY()/32);
		count = 0;
		GL11.glPushMatrix();
		GL11.glTranslatef(view.getPosition().getX(),view.getPosition().getY(),0);
		for (int x = viewX; x < (viewX+view.getViewWidth()); x++)
		{
			for (int y = viewY; y < (viewY+view.getViewHeight()); y++)
			{
				String key = x + "," + y;
				if (entities.containsKey(key))
				{
					count++;
					Entity entity = entities.get(key);
					Quad quad = quads.get(entity.getQuadName());
					GL11.glPushMatrix();
					GL11.glTranslatef(entity.getPosition().getX(), entity.getPosition().getY(), 0);
					GL11.glRotatef(entity.getRotation(), 0, 0, 1);
					GL11.glTranslatef(-entity.getOrigin().getX() * entity.getScale().getX(),
							-entity.getOrigin().getY() * entity.getScale().getY(), 0);
					GL11.glScalef(entity.getScale().getX(), entity.getScale().getY(), 0);
					GL11.glColor3f(1, 1, 1);
					GL11.glBindTexture(0, quad.getTextureID());
					GL11.glBegin(quad.getRenderType());
					glCallList(quad.getDisplayID());
					GL11.glEnd();
					GL11.glPopMatrix();
				}
			}
		}
		GL11.glPopMatrix();
		System.out.println("Count:"+count);
	}

	public void Update()
	{

	}

	public void AssessEntities()
	{

	}

	public void addEntity(Entity entity)
	{
		String key = (int)(entity.getPosition().getX()/32)+","+(int)(entity.getPosition().getY()/32);
		entities.put(key,entity);
	}
}
