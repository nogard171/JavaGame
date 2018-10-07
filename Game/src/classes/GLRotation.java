package classes;

public class GLRotation {
	private float rotation = 0;

	public GLRotation() {

	}

	public GLRotation(float newRotation) {
		this.SetRotation(newRotation);
	}

	public void SetRotation(float newRotation) {
		this.rotation = newRotation;
	}

	public float GetRotation() {
		return this.rotation;
	}
}
