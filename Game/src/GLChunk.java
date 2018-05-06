import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GLChunk {
	public Vector2f position = new Vector2f(0, 0);
	GLObjectStack[][] data;

	public GLChunk(int x, int y, int width, int height) {
		this.position = new Vector2f(x, y);
		initChunk(width, height);
	}

	public GLChunk(int width, int height) {
		initChunk(width, height);

	}

	public void initChunk(int width, int height) {
		data = new GLObjectStack[width][height];
		for (int y = 0; y < data.length; y++) {
			for (int x = 0; x < data[y].length; x++) {
				x += (int) this.position.x;
				y += (int) this.position.y;
				GLObjectStack objectStack = new GLObjectStack();
				GLObject obj = new GLObject("GRASS");
				obj.bounds = new Rectangle((x * 32) - (y * 32), (y * 16) + (x * 16), 16, 16);
				int newX = (x * 32) - (y * 32);
				int newY = (y * 16) + (x * 16);
				Point[] newPolygon = { new Point(newX, newY), new Point(newX + 32, newY + 16),
						new Point(newX, newY + 64), new Point(newX - 32, newY + 16) };
				obj.polygon = newPolygon;

				objectStack.objects.add(obj);
				x -= (int) this.position.x;
				y -= (int) this.position.y;
				data[y][x] = objectStack;
			}
		}
	}

	public void moveObject(GLChunk chunk, int newX, int newY, String name) {

		GLObjectIndex index = findObjectIndexByName(name);
		if (index != null) {
			GLObject obj = index.chunk.data[(int) index.index.x][(int) index.index.y].objects.get((int) index.index.z);
			
			chunk.data[newX][newY].objects.add(obj);
			index.chunk.data[(int) index.index.x][(int) index.index.y].objects.remove((int) index.index.z);
		}
	}

	public GLObjectIndex findObjectIndexByName(String name) {
		GLObjectIndex objectIndex = null;
		for (GLChunk chunk : new GLDataHub().chunks) {			
			for (int y = 0; y < chunk.data.length; y++) {
				for (int x = 0; x < chunk.data[y].length; x++) {
					GLObjectStack objectStack = chunk.data[x][y];
					for (int o = 0; o < objectStack.objects.size(); o++) {
						if (objectStack.objects.get(o).name.equals(name)) {
							objectIndex = new GLObjectIndex();
							objectIndex.chunk = chunk;
							System.out.println(chunk.position);
							objectIndex.index = new Vector3f(x, y, o);
							break;
						}
					}
				}
			}
		}

		return objectIndex;
	}

	public GLObject findObjectByNameInStack(GLObjectStack objectStack, String name) {
		GLObject object = null;

		for (int o = 0; o < objectStack.objects.size(); o++) {
			if (objectStack.objects.get(o).name.equals(name)) {
				object = objectStack.objects.get(o);
				break;
			}
		}

		return object;
	}

	public void Render() {
		GLShader shader = new GLDataHub().shader;
		float[] chunkPosition = { (this.position.x * 32) - (this.position.y * 32),
				(this.position.y * 16) + (this.position.x * 16), 0 };

		shader.sendUniform3f("chunkPosition", chunkPosition);
		for (int y = 0; y < data.length; y++) {
			for (int x = 0; x < data[y].length; x++) {
				GLObjectStack objectStack = data[y][x];

				float[] position = { (x * 32) - (y * 32), (y * 16) + (x * 16), 0 };

				shader.sendUniform3f("position", position);
				for (int o = 0; o < objectStack.objects.size(); o++) {
					String model = data[y][x].objects.get(o).type;
					int displayList = new GLDataHub().models.get(model).displayListID;
					GL11.glCallList(displayList);
				}
			}
		}
	}
}
