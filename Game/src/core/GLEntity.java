package core;

public class GLEntity extends GLObject {

	public void Move(GLVelocity velocity) {
		if (velocity.hasVelocity()) {
			this.setPosition(this.getPosition().getX() + velocity.getXVelocity(),
					this.getPosition().getY() + velocity.getYVelocity());
		}
	}
}
