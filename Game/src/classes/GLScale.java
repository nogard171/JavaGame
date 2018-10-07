package classes;

public class GLScale {
	private float scale = 0;

	public GLScale() {

	}

	public GLScale(float newScale) {
		this.SetScale(newScale);
	}

	public void SetScale(float newScale) {
		this.scale = newScale;
	}

	public float GetScale() {
		return this.scale;
	}
}
