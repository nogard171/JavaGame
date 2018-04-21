package engine;

import org.lwjgl.util.vector.Vector2f;

public class GLTransform extends GLComponent {
	private Vector2f Position = new Vector2f(0, 0);
	private Vector2f Velocity = new Vector2f(0, 0);
	private float Rotation = 0;
	private Vector2f Center = new Vector2f(16, 16);

	public Vector2f getCenter() {
		return Center;
	}

	public void setCenter(Vector2f center) {
		Center = center;
	}

	public GLTransform() {
		this.setName("transform");
	}

	public GLTransform(float x, float y) {
		this.setPosition(x, y);
		this.setName("transform");
	}

	public GLTransform(Vector2f newPosition) {
		this.setPosition(newPosition);
		this.setName("transform");
	}

	public float getRotation() {
		return this.Rotation;
	}

	public void setRotation(float rotation) {
		this.Rotation = rotation;
	}

	public void Rotate(float amount) {
		this.Rotation = this.getRotation() + amount;
	}

	public Vector2f getPosition() {
		return this.Position;
	}

	public void setPosition(float x, float y) {
		this.setPosition(new Vector2f(x, y));
	}

	public void setPosition(Vector2f newPosition) {
		this.Position = newPosition;
	}

	public void Move(Vector2f directions) {
		this.setVelocity(new Vector2f(directions.x,directions.y));
	}

	public void Move(float x, float y) {
		this.Move(new Vector2f(x, y));
	}
	
	public void Update()
	{
		this.Position = new Vector2f(this.getPosition().getX() + this.getVelocity().getX(),
				this.getPosition().getY() + this.getVelocity().getY());
	}

	public Vector2f getVelocity() {
		return Velocity;
	}

	public void setVelocity(Vector2f velocity) {
		Velocity = velocity;
	}
}
