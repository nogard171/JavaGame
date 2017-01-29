import static org.lwjgl.opengl.GL11.glCallList;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class Renderer
{

	ShaderProgram shader = new ShaderProgram();
	private HashMap<String, Quad> quads = new HashMap<String, Quad>();
	 private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> entitiesToDisplay = new ArrayList<Entity>();

	public ArrayList<Entity> getEntitiesToDisplay()
	{
		return entitiesToDisplay;
	}

	//private HashMap<String, Entity> entities = new HashMap<String, Entity>();

	int renderWidth = 0;
	int renderHeight = 0;

	public void Init()
	{
		this.renderWidth = (int) (Display.getWidth() / 32);
		this.renderHeight = (int) (Display.getHeight() / 32);

		shader.loadFragmentShader("screen");
		shader.loadVertexShader("screen");
		shader.createProgram();

		// GL11.glEnable(GL11.GL_TEXTURE_2D);

		new Loader();
		quads.put("GRASS", Loader.GenerateQuadWithTexture("grass.png"));
		quads.put("DIRT", Loader.GenerateQuadWithTexture("dirt.png"));
		quads.put("SAND", Loader.GenerateQuadWithTexture("sand.png"));
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);

	}

	int count = 0;
	View view = new View();

	public void Render(View newView)
	{
		view = newView;
		this.AssessEntities();
		shader.Start();

		int viewX = (int) Math.floor(-view.getPosition().getX() / 32);
		int viewY = (int) Math.floor(-view.getPosition().getY() / 32);
		int viewWidth = (int) Math.ceil(-view.getPosition().getX() / 32) + view.getViewWidth();
		int viewHeight = (int) Math.ceil(-view.getPosition().getY() / 32) + view.getViewHeight();
		count = 0;
		GL11.glPushMatrix();
		GL11.glTranslatef(view.getPosition().getX(), view.getPosition().getY(), 0);
		for (Entity entity : entitiesToDisplay)
		{
			Quad quad = quads.get(entity.getQuadName());

			// GL11.glBindTexture(0, quad.getTextureID());
			shader.sendTexture(quad.getTextureID());

			GL11.glPushMatrix();
			GL11.glTranslatef(entity.getPosition().getX(), entity.getPosition().getY(), 0);
			GL11.glRotatef(entity.getRotation(), 0, 0, 1);
			GL11.glTranslatef(-entity.getOrigin().getX() * entity.getScale().getX(),
					-entity.getOrigin().getY() * entity.getScale().getY(), 0);
			GL11.glScalef(entity.getScale().getX(), entity.getScale().getY(), 0);
			// GL11.glColor3f(entity.getColor().getRed(),
			// entity.getColor().getGreen(), entity.getColor().getBlue());

			shader.send4f("entityColor", entity.getColor().getRed()/ 255f, entity.getColor().getGreen()/ 255f,
					entity.getColor().getBlue()/ 255f, entity.getColor().getAlpha()/ 255f);

			GL11.glBegin(quad.getRenderType());
			glCallList(quad.getDisplayID());
			GL11.glEnd();
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}

	public void Update()
	{

	}
	public Entity getEntity(String name)
	{
		Entity entity = null;
		for (Entity newEntity : entitiesToDisplay)
		{
			if (newEntity.getName().toUpperCase().equals(name.toUpperCase()))
			{
				entity = newEntity;
				break;
			}
		}
		return entity;
	}
	public Entity getHoveredEntity()
	{
		Entity entity = null;
		Point mousePoint = new Point((int) -view.getPosition().getX() + Mouse.getX(),
				(int) -view.getPosition().getY() + Mouse.getY());
		for (Entity newEntity : entitiesToDisplay)
		{
			Rectangle rec = new Rectangle((int) newEntity.getPosition().x, (int) newEntity.getPosition().y, 32, 32);
			if (rec.contains(mousePoint))
			{
				entity = newEntity;
				break;
			}
		}
		return entity;
	}

	public void AssessEntities()
	{
		/*entitiesToDisplay.clear();
		int viewX = (int) Math.floor(-view.getPosition().getX() / 32);
		int viewY = (int) Math.floor(-view.getPosition().getY() / 32);
		int viewWidth = (int) Math.ceil(-view.getPosition().getX() / 32) + view.getViewWidth();
		int viewHeight = (int) Math.ceil(-view.getPosition().getY() / 32) + view.getViewHeight();
		count = 0;
		for (int x = viewX; x < viewWidth; x++)
		{
			for (int y = viewY; y < viewHeight; y++)
			{
				String key = x + "," + y;
				if (entities.containsKey(key))
				{
					Entity entity = entities.get(key);
					entitiesToDisplay.add(entity);
					count++;
				}
			}
		}*/
		this.entitiesToDisplay.clear();
		Rectangle viewBound = new Rectangle((int)-view.getPosition().x,(int)-view.getPosition().y,(int)view.getViewWidth()*32,(int)view.getViewHeight()*32);
		for (Entity newEntity : this.entities)
		{
			if(viewBound.contains(new Rectangle((int)newEntity.getPosition().getX(),(int)newEntity.getPosition().getY(),32,32)))
			{
				this.entitiesToDisplay.add(newEntity);
			}
		}
		
		//System.out.println("Count:" + count);
	}
	public void addEntity(Entity entity)
	{
		String key = (int) (entity.getPosition().getX() / 32) + "," + (int) (entity.getPosition().getY() / 32);
		//entities.put(key, entity);
		entities.add(entity);
	}

	public void Destroy()
	{
		for (Quad quad : quads.values())
		{
			GL11.glDeleteLists(quad.getDisplayID(), GL11.GL_COMPILE);
		}
	}
}
