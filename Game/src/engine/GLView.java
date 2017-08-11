package engine;

import java.awt.Rectangle;

public class GLView {
	public int X = 0;
	public int Y = 0;
	public int Width = 0;
	public int Height = 0;
	public Boolean update = false;

	public GLView(int i, int j, int k, int l) {
		this.X = i;
		this.Y = j;
		this.Width = k;
		this.Height = l;
	}

	public boolean isObjectInView(GLObject obj) {
		boolean isInside = false;
		GLTransform transform = (GLTransform) obj.getComponent("transform");
		GLMaterial mat = (GLMaterial) obj.getComponent("material");
		if (transform != null && mat != null) {
			if (new Rectangle(this.X, this.Y, this.Width, this.Height).intersects(
					new Rectangle((int) transform.getPosition().getX(), (int) transform.getPosition().getY(),
							(int) mat.getTextureSize().getWidth(), (int) mat.getTextureSize().getHeight()))) {
				isInside= true;
			}
		}
		return isInside;
	}
}