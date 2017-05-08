import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Entity
{
	private String name = "";
	private String quadName = "";
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	private Vector2f position;
	private float rotX,rotY;
	private Vector2f scale = new Vector2f(1,1);
	private float originX,originY;
	public float getOriginX()
	{
		return originX;
	}
	public float getOriginY()
	{
		return originY;
	}
	public void setOrigin(float originX,float originY)
	{
		this.originX = originX;
		this.originY = originY;
	}
	public Entity(String quadName)
	{
		this.quadName = quadName;
		position = new Vector2f(0,0);
	}
	public String getQuadName()
	{
		return quadName;
	}
	public void setQuadName(String quadName)
	{
		this.quadName = quadName;
	}
	public Vector2f getPosition()
	{
		return position;
	}
	public void setPosition(Vector2f position)
	{
		this.position = position;
	}
	public void setPosition(float x, float y)
	{
		this.position = new Vector2f(x,y);
	}
	public float getRotX()
	{
		return rotX;
	}
	public void setRotX(float rotX)
	{
		this.rotX = rotX;
	}
	public float getRotY()
	{
		return rotY;
	}
	public void setRotY(float rotY)
	{
		this.rotY = rotY;
	}
	public Vector2f getScale()
	{
		return scale;
	}
	public void setScale(Vector2f scale)
	{
		this.scale = scale;
	}
}
