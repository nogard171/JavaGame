import org.lwjgl.util.Point;

public class Entity extends Object {
	float speed = 1;
	int jump = 0;
	Direction dir = Direction.WEST;
	boolean moving = false;
	public int velocity_rate = 1;

	public Entity() {
	}

	public void Move(int x_Speed, int y_Speed) {
		moving = true;
		if (x_Speed > 0) {
			dir = Direction.EAST;
		} else if (x_Speed < 0) {
			dir = Direction.WEST;
		}

		this.position.setX(this.position.getX() + (int) ((x_Speed * speed) * velocity_rate));
		this.position.setY(this.position.getY() + (int) ((y_Speed * speed) * velocity_rate));
	}

	public void EndMove() {
		moving = false;
	}

	public void Jump(float delta) {

	}

	public void Update() {

	}

	public void Render() {
		super.setPosition(super.position.getX(), super.position.getY());
		this.Update();
		super.Render();

		// sprite.VertexRender();
	}
}
