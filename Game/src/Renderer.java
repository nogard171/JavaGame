import static org.lwjgl.opengl.GL11.glCallList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Renderer
{
	HashMap<String,Quad> quads = new HashMap<String,Quad>();
	ArrayList<Entity> entities = new ArrayList<Entity>();

	public void Render()
	{		
		for (Entity entity : entities)
		{
			Quad quad = quads.get(entity.getQuadName());
			//shader.sendTexture("myTexture", quad.getTextureID());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, quad.getTextureID());
			GL11.glPushMatrix();
			GL11.glTranslatef(entity.getPosition().getX(), entity.getPosition().getY(), 0);
			GL11.glRotatef(entity.getRotX(), 0f, 0f, 1f);
			GL11.glRotatef(entity.getRotY(), 0f, 1f, 0f);
			GL11.glScalef(entity.getScale().getX()+quad.getSize().getWidth(),entity.getScale().getY()+quad.getSize().getHeight(), 1);
			GL11.glTranslatef(-entity.getOriginX(), -entity.getOriginY(), 0);
			glCallList(quad.getDlid());
			GL11.glPopMatrix();
		}
	}

	public Entity getEntity(String name)
	{
		Entity newEntity = null;
		for (Entity entity : entities)
		{
			if(entity.getName()==name)
			{
				newEntity = entity;
				break;
			}
		}
		return newEntity;
	}

	public void addEntity(String name, Entity entity)
	{
		entity.setName(name);
		entities.add(entity);
	}

	Loader loader = new Loader();

	public void addQuad(String quadName, String imageName)
	{
		Quad quad = new Quad();
		loader.loadQuad(quad,imageName);
		quads.put(quadName,quad);
	}
	public void addQuad(Quad quad, String name)
	{
		quad.setSize(32, 32);
		quads.put(name,quad);
	}
}
