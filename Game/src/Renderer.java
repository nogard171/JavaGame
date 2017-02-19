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
	ShaderProgram shader = new ShaderProgram();
	public void setShader(ShaderProgram shader)
	{
		this.shader = shader;
	}

	HashMap<String,Quad> quads = new HashMap<String,Quad>();
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	Viewport view = new Viewport();
	public void Render()
	{		
		shader.Start();
		GL11.glPushMatrix();
		GL11.glTranslatef(view.getPosition().getX(), view.getPosition().getY(),0);
		
		for (Entity entity : entities)
		{
			Quad quad = quads.get(entity.getQuadName());
			shader.sendTexture("myTexture", quad.getTextureID());
			GL11.glPushMatrix();
			GL11.glTranslatef(entity.getPosition().getX(), entity.getPosition().getY(), 0);
			GL11.glRotatef(entity.getRotX(), 0f, 0f, 1f);
			GL11.glRotatef(entity.getRotY(), 0f, 1f, 0f);
			GL11.glScalef(entity.getScale().getX()+quad.getSize().getWidth(),entity.getScale().getY()+quad.getSize().getHeight(), 1);
			GL11.glTranslatef(-entity.getOriginX(), -entity.getOriginY(), 0);
			
			glCallList(quad.getDlid());
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
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
}
