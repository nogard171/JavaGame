package engine;

public class GLClickable extends GLComponent {
	public GLMouse mouse = new GLMouse();
	GLRectangle bounds = new GLRectangle();
	public boolean hovered = false;
	public boolean clicked = false;

	public GLClickable() {
		this.setName("clickable");
	}

	public void Run() {
		if (this.getObject() != null) {
			GLTransform transform = (GLTransform) this.getObject().getComponent("transform");
			if (transform != null) {
				GLMaterial mat = (GLMaterial) this.getObject().getComponent("material");
				if (mat != null) {
					this.bounds = new GLRectangle(transform.getPosition().getX(), transform.getPosition().getY(),
							mat.getFrameSize().getWidth(), mat.getFrameSize().getHeight());

					if (this.bounds.inside(new GLRectangle(mouse.getMouseX(), mouse.getMouseY(), 1, 1))) {
						hovered = true;
					} else {
						hovered = false;
					}

					if (this.hovered && mouse.isMouseDown(0)) {
						clicked = true;
					} else {
						clicked = false;
					}
				}
			}
		}
	}
}
