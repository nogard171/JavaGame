package engine;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class GLCollider extends GLComponent {
	GLRectangle bounds = new GLRectangle();

	public GLRectangle getBounds() {
		return bounds;
	}

	public void setBounds(GLRectangle bounds) {
		this.bounds = bounds;
	}

	public GLCollider() {
		this.setName("collider");

	}

	public GLCollider(float X, float Y, float Width, float Height) {
		this.setName("collider");
		this.setBounds(new GLRectangle(X, Y, Width, Height));
	}

	public void Run(ArrayList<GLObject> objectToUpdate) {
		GLPhysics physics = (GLPhysics) this.getObject().getComponent("physics");
		if (physics != null) {
			GLTransform transform = (GLTransform) this.getObject().getComponent("transform");
			if (transform != null) {
				GLMaterial mat = (GLMaterial) this.getObject().getComponent("material");
				if (mat != null) {
					/*
					 * GLRectangle bounds = new
					 * GLRectangle(transform.getPosition().getX(),
					 * transform.getPosition().getY(),
					 * mat.getFrameSize().getWidth(),
					 * mat.getFrameSize().getHeight());
					 */
					GLRectangle objBounds = new GLRectangle(transform.getPosition().getX() + this.getBounds().getX(),
							transform.getPosition().getY() + this.getBounds().getY(), +this.getBounds().getWidth(),
							+this.getBounds().getHeight());

					renderGrid(objBounds.X, objBounds.Y, objBounds.Width, objBounds.Height);

					for (GLObject obj : objectToUpdate) {
						if (this.getObject() != obj) {
							GLPhysics otherPhysics = (GLPhysics) obj.getComponent("physics");
							if (otherPhysics != null) {
								GLTransform otherTransform = (GLTransform) obj.getComponent("transform");
								if (transform != null) {
									GLMaterial otherMat = (GLMaterial) obj.getComponent("material");
									if (mat != null) {
										GLRectangle otherBounds = new GLRectangle(otherTransform.getPosition().getX(),
												otherTransform.getPosition().getY(), otherMat.getFrameSize().getWidth(),
												otherMat.getFrameSize().getHeight());

										renderGrid(otherBounds.X, otherBounds.Y, otherBounds.Width, otherBounds.Height);
										if (objBounds.intersects(otherBounds)) {
											System.out.println("colliding:");
											otherMat.setColor(255, 0, 0);
										} else {
											otherMat.setColor(255,255,255);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void renderGrid(float X, float Y, float Width, float Height) {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		GL11.glColor3f(1, 0, 0);
		GL11.glPushMatrix();
		GL11.glTranslatef(X, Y, 0);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(Width, 0);
		GL11.glVertex2f(Width, Height);
		GL11.glVertex2f(0, Height);

		GL11.glEnd();
		GL11.glPopMatrix();
	    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}
}
