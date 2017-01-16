import static org.lwjgl.opengl.GL11.glCallList;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class Renderer
{
	Loader loader = new Loader();
	HashMap<String, Quad> quads = new HashMap<String, Quad>();
	
	ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	ArrayList<Chunk> chunksToDisplay = new ArrayList<Chunk>();


	public void Render()
	{
		for (Chunk chunk : this.chunksToDisplay)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(chunk.getX(), chunk.getY(), 0);
			for (Entity entity : chunk.entities)
			{
				Quad quad = quads.get(entity.getQuadName());
				// shader.sendTexture("myTexture", quad.getTextureID());
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, quad.getTextureID());
				GL11.glPushMatrix();
				GL11.glTranslatef(entity.getPosition().getX(), entity.getPosition().getY(), 0);
				GL11.glRotatef(entity.getRotX(), 0f, 0f, 1f);
				GL11.glRotatef(entity.getRotY(), 0f, 1f, 0f);
				GL11.glScalef(entity.getScale().getX() + quad.getSize().getWidth(),
						entity.getScale().getY() + quad.getSize().getHeight(), 1);
				GL11.glTranslatef(-entity.getOriginX(), -entity.getOriginY(), 0);
				glCallList(quad.getDlid());
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();
		}
		for (Rectangle rec:recs)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float)rec.getX(), (float)rec.getY(), 0);
			
			GL11.glPopMatrix();
		}
		
		
	}
	ArrayList<Rectangle> recs = new ArrayList<Rectangle>();
	public void hoverChunk(Point mousePoint)
	{
		recs.clear();
		for (Chunk chunk : this.chunksToDisplay)
		{
			for (Entity entity : chunk.entities)
			{
				Rectangle rec = new Rectangle(((int)entity.getPosition().getX()+(int)chunk.getX())-16,((int)entity.getPosition().getY()+(int)chunk.getY())+32,32,32);
				if(rec.contains(mousePoint))
				{
					entity.setOrigin(0,-1);
					Display.setTitle("Chunk:"+chunk.name+"/Type:" + entity.getQuadName());
				}			
				else
				{
					entity.setOrigin(0,0);
				}
				recs.add(rec);
			}
		}
	}

	int oldX = 0;
	int oldY = 0;
	int tileWidth = 0;
	int tileHeight = 0;

	public void UpdateDisplayEntities(Camera camera)
	{
		chunksToDisplay.clear();
		Rectangle windoView = new Rectangle((int)camera.getPosition().getX(),(int)camera.getPosition().getY(),Display.getWidth(),Display.getHeight());
		for (Chunk chunk : this.chunks)
		{
			if(windoView.contains(chunk.getBounds()))
			{
				chunksToDisplay.add(chunk);
			}
		}
	}

	public void addQuad(String quadName, String imageName)
	{
		Quad quad = new Quad();
		loader.loadQuad(quad, imageName);
		quads.put(quadName, quad);
	}

	public void addQuad(Quad quad, String name)
	{
		quad.setSize(32, 32);
		quads.put(name, quad);
	}

	public void addChunk(String chunkName, Chunk chunk)
	{
		chunk.name = chunkName;
		chunks.add(chunk);
	}

	public Entity getEntity(String name)
	{
		Entity entity = null;
		for (Chunk chunk : this.chunks)
		{
			for (Entity oldEntity : chunk.entities)
			{
				if(oldEntity.getName() == name)
				{
					entity = oldEntity;
					break;
				}
			}
		}
		return entity;
	}

	public Entity getHovered(Point mousePoint)
	{
		Entity newEntity = null;
		for (Chunk chunk : this.chunksToDisplay)
		{
			for (Entity entity : chunk.entities)
			{
				Rectangle rec = new Rectangle(((int)entity.getPosition().getX()+(int)chunk.getX())-16,((int)entity.getPosition().getY()+(int)chunk.getY())+32,32,32);
				if(rec.contains(mousePoint))
				{
					newEntity = entity;
					break;
				}
			}
		}
		return newEntity;
	}
	public String getHoveredKey(Point mousePoint)
	{
		String key = "";
		for (Chunk chunk : this.chunksToDisplay)
		{
			for (Entity entity : chunk.entities)
			{
				Rectangle rec = new Rectangle(((int)entity.getPosition().getX()+(int)chunk.getX())-16,((int)entity.getPosition().getY()+(int)chunk.getY())+32,32,32);
				if(rec.contains(mousePoint))
				{
					key = entity.getName();
					break;
				}
			}
		}
		return key;
	}
}
