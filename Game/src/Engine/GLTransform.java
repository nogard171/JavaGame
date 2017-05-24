package Engine;

import org.lwjgl.util.vector.Vector2f;

public class GLTransform extends GLComponent{
	private Vector2f Position = new Vector2f(0, 0);
	private float Rotation = 0;

	public GLTransform()
	{
		this.setName("transform");
	}
	
	public GLTransform(float x, float y)
	{
		this.setPosition(x, y);
		this.setName("transform");
	}
	
	public GLTransform(Vector2f newPosition)
	{
		this.setPosition(newPosition);
		this.setName("transform");
	}
	
	public float getRotation() {
		return Rotation;
	}

	public void setRotation(float rotation) {
		Rotation = rotation;
	}

	public Vector2f getPosition() {
		return Position;
	}

	public void setPosition(float x, float y) {
		this.setPosition(new Vector2f(x,y));
	}

	public void setPosition(Vector2f newPosition) {
		this.Position = newPosition;
	}

	public void Move(Vector2f directions) {
		this.Position = new Vector2f(this.getPosition().getX() + directions.getX(),
				this.getPosition().getY() + directions.getY());
	}
	public void Move(float x,float y) {
		this.Move(new Vector2f(x,y));
	}
}
