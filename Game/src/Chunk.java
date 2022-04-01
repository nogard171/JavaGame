import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class Chunk {
	int displayListHandle = -1;
	Vector3f position = new Vector3f(0, 0, 0);
	Material[][][] data;
	Vector3f size = new Vector3f(8, 8, 8);
	ArrayList<Material> cubes = new ArrayList<Material>();

	public Chunk() {
		loadChunk();
	}

	public Chunk(int i, int j, int k) {
		// TODO Auto-generated constructor stub
		loadChunk();
		position = new Vector3f(i, j, k);
	}

	public void loadChunk() {
		data = new Material[(int) size.y][(int) size.x][(int) size.z];

		for (int y = 0; y < size.y; y++) {
			for (int x = 0; x < size.x; x++) {
				for (int z = 0; z < size.z; z++) {

					data[y][x][z] = new Material();
				}
			}
		}
	}

	public void renderChunk(float step) {
		if (displayListHandle == -1) {

			displayListHandle = GL11.glGenLists(1);

			// Start recording the new display list.
			GL11.glNewList(displayListHandle, GL11.GL_COMPILE);

			// End the recording of the current display list.

			GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
			for (int y = 0; y < size.y; y++) {
				for (int x = 0; x < size.x; x++) {
					for (int z = 0; z < size.z; z++) {
						Material mat = data[y][x][z];
						if (mat != null) {
							renderCube(new Vector3f(x, y, z), step, mat);
						}

					}
				}
			}
			GL11.glPopMatrix();

			GL11.glEndList();
		} else {
			GL11.glCallList(displayListHandle);
		}

	}

	public void renderCube(Vector3f vec, float step, Material mat) {
		float x = vec.x;
		float y = vec.y;
		float z = vec.z;
		GL11.glBegin(GL11.GL_QUADS);
		Material check = null;
		if (y + 1 < size.y) {
			check = data[(int) y + 1][(int) x][(int) z];
		}
		Vector2f tex = null;
		if (check == null) {
			tex = mat.vec[0];
			// top
			GL11.glTexCoord2f((tex.x) * step, (tex.y) * step);
			GL11.glVertex3f(x + 1, y + 1, z);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y) * step);
			GL11.glVertex3f(x, y + 1, z);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x, y + 1, z + 1);
			GL11.glTexCoord2f((tex.x) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x + 1, y + 1, z + 1);
		}

		check = null;
		if (y - 1 >= 0) {
			check = data[(int) y - 1][(int) x][(int) z];
		}
		if (check == null) {
			tex = mat.vec[1];
			// bottom
			GL11.glTexCoord2f((tex.x) * step, (tex.y) * step);
			GL11.glVertex3f(x + 1, y, z + 1);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y) * step);
			GL11.glVertex3f(x, y, z + 1);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x, y, z);
			GL11.glTexCoord2f((tex.x) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x + 1, y, z);
		}

		check = null;
		if (z - 1 >= 0) {
			check = data[(int) y][(int) x][(int) z - 1];
		}
		if (check == null) {
			tex = mat.vec[2];
			// south
			GL11.glTexCoord2f((tex.x) * step, (tex.y) * step);
			GL11.glVertex3f(x + 1, y, z);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y) * step);
			GL11.glVertex3f(x, y, z);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x, y + 1, z);
			GL11.glTexCoord2f((tex.x) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x + 1, y + 1, z);
		}

		check = null;
		if (z + 1 < size.z) {
			check = data[(int) y][(int) x][(int) z + 1];
		}
		if (check == null) {
			tex = mat.vec[3];
			// north
			GL11.glTexCoord2f((tex.x) * step, (tex.y) * step);
			GL11.glVertex3f(x, y, z + 1);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y) * step);
			GL11.glVertex3f(x + 1, y, z + 1);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x + 1, y + 1, z + 1);
			GL11.glTexCoord2f((tex.x) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x, y + 1, z + 1);
		}

		check = null;
		if (x + 1 < size.x) {
			check = data[(int) y][(int) x + 1][(int) z];
		}
		if (check == null) {
			tex = mat.vec[4];
			// east
			GL11.glTexCoord2f((tex.x) * step, (tex.y) * step);
			GL11.glVertex3f(x + 1, y, z + 1);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y) * step);
			GL11.glVertex3f(x + 1, y, z);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x + 1, y + 1, z);
			GL11.glTexCoord2f((tex.x) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x + 1, y + 1, z + 1);
		}

		check = null;
		if (x - 1 >= 0) {
			check = data[(int) y][(int) x - 1][(int) z];
		}
		if (check == null) {
			tex = mat.vec[5];
			// west
			GL11.glTexCoord2f((tex.x) * step, (tex.y) * step);
			GL11.glVertex3f(x, y, z);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y) * step);
			GL11.glVertex3f(x, y, z + 1);
			GL11.glTexCoord2f((tex.x + 1) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x, y + 1, z + 1);
			GL11.glTexCoord2f((tex.x) * step, (tex.y + 1) * step);
			GL11.glVertex3f(x, y + 1, z);
		}
		GL11.glEnd();
	}
}
