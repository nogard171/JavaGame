package classes;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import engine.GLWindow;

public class GLWorld {
	ArrayList<GLEntity> entities = new ArrayList<GLEntity>();

	ArrayList<GLEntity> entitiesToRender = new ArrayList<GLEntity>();
	int displayList = -1;

	public GLWorld() {
		int width = 3;
		int height = 3;
		int depth = 1;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < depth; z++) {
					GLEntity entity = new GLEntity();

					entities.add(entity);
				}
			}
		}

	}

	public void render(GLView view) {
		GL11.glColor3f(1, 1, 1);
		if (displayList == -1) {
			// displayList = GL11.glGenLists(1);
			// GL11.glNewList(displayList, GL11.GL_COMPILE);
			for (GLEntity entity : this.entities) {

				float x = entity.bound.position.getX();
				float y = entity.bound.position.getY();
				float z = entity.bound.position.getZ();

				GLBound bound = new GLBound(x + vec.offset.getX(), y + vec.offset.getY(), vec.size.getWidth(),
						vec.size.getHeight());
				square(bound, vec.textureData);

			}
			// GL11.glEndList();
		} else {
			// GL11.glCallList(displayList);
		}
	}

	public void square(GLBound bound, GLTextureData textureData) {
		GL11.glBegin(GL11.GL_QUADS);
		float newX = bound.position.getX();
		float newY = bound.position.getY();
		float width = bound.size.getWidth();
		float height = bound.size.getHeight();

		GL11.glTexCoord2f(textureData.getX(), textureData.getY());
		GL11.glVertex2f(newX, newY);

		GL11.glTexCoord2f(textureData.getX() + textureData.getWidth(), textureData.getY());
		GL11.glVertex2f(newX + width, newY);

		GL11.glTexCoord2f(textureData.getX() + textureData.getWidth(), textureData.getY() + textureData.getHeight());
		GL11.glVertex2f(newX + width, newY + height);

		GL11.glTexCoord2f(textureData.getX(), textureData.getY() + textureData.getHeight());
		GL11.glVertex2f(newX, newY + height);

		GL11.glEnd();
	}
}
