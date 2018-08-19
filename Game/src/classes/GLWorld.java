package classes;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import engine.GLWindow;

public class GLWorld {
	ArrayList<GLVector> vectors = new ArrayList<GLVector>();

	ArrayList<GLVector> vectorsToRender = new ArrayList<GLVector>();
	int displayList = -1;

	public GLWorld() {
		int width = 3;
		int height = 3;
		int depth = 1;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < depth; z++) {
					int newX = (x - y) * 32;
					int newY = (y + x) * 16;// - (z * 32);
					GLVector vec = new GLVector();
					vec.position = new GLPosition(newX, newY);
					vec.normal = new GLPosition(0, 1, 0);
					vec.size = new GLSize(64, 64);
					GLTextureData textureData = new GLTextureData(0, 0, 0.25f, 0.25f);
					if (x == 0 && y == 0 && z == 0) {

						textureData.position = new GLPosition(0.25f, 0);
					}
					vec.textureData = textureData;

					if (x >= width - 1 || y >= height - 1||z >= depth - 1) {
						vectors.add(vec);
					}
					
				}
			}
		}

		int newX = (1 - 0) * 32;
		int newY = (0 + 1) * 16 - ((1) * 32);
		GLVector vec = new GLVector();
		vec.position = new GLPosition(newX, newY);
		vec.offset = new GLPosition(0, -32);
		vec.normal = new GLPosition(0, 1, 0);
		vec.size = new GLSize(64, 128);
		GLTextureData textureData = new GLTextureData(0f, 0.25f, 0.25f, 0.5f);
		vec.textureData = textureData;
		//vectors.add(vec);

	}

	public void updateView(GLView view) {
		vectorsToRender.clear();
	
		for (int i = 0; i < vectors.size(); i++) {
			GLVector vec = vectors.get(i);
			
			if (view.bound.containsPosition(vec.position)) {
				vectorsToRender.add(vec);
			}

			else
			{
				System.out.println("X: " + view.bound.position.getX() + " == X: " + vec.position.getX() + "\nY: "+ view.bound.position.getY() + " == Y: " + vec.position.getY());
			}
		}
		System.out.println(vectorsToRender.size());
	}

	public void render(GLView view) {
		updateView(view);
		GL11.glColor3f(1, 1, 1);
		if (displayList == -1) {
			// displayList = GL11.glGenLists(1);
			// GL11.glNewList(displayList, GL11.GL_COMPILE);
			for (int i = 0; i < vectorsToRender.size(); i++) {
				GLVector vec = vectors.get(i);
				float x = vec.position.getX();
				float y = vec.position.getY();
				float z = vec.position.getZ();

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
