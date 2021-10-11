import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class Chunk {
	int displayListHandle = -1;
	Vector3f position = new Vector3f(0, 0, 0);
	Vector3f index = new Vector3f(0, 0, 0);
	public static Material[][][] data;
	Vector3f size = new Vector3f(16, 16, 16);
	ArrayList<Material> cubes = new ArrayList<Material>();

	public Chunk() {
		loadChunk();
	}

	public Chunk(int i, int j, int k) {
		loadChunk();
		position = new Vector3f(i, j, k);
		index = new Vector3f(i / 16, j / 16, k / 16);
	}

	public void loadChunk() {
		data = new Material[(int) size.y][(int) size.x][(int) size.z];

		for (int y = 0; y < size.y; y++) {
			for (int x = 0; x < size.x; x++) {
				for (int z = 0; z < size.z; z++) {

					if (x == 0 && y == 14 && z == 0) {
						data[y][x][z] = new Material(Type.AIR);
					} else {
						data[y][x][z] = new Material(Type.GRASS);
					}
				}
			}
		}
	}

	Type[] blankTypes = { Type.AIR, Type.BLANK };

	public boolean containsType(Type[] types, Type newType) {
		boolean containsType = false;
		for (int i = 0; i < types.length; i++) {

			if (types[i].toString() == newType.toString()) {
				containsType = true;
				break;
			}
		}
		return containsType;
	}

	public boolean isCubeBlank(Material mat) {
		boolean isBlank = false;
		if (mat != null) {
			if (containsType(blankTypes, mat.type)) {
				isBlank = true;
			}
		} else {
			isBlank = true;
		}
		return isBlank;
	}

	public void renderChunk(float step) {
		if (displayListHandle == -1) {

			displayListHandle = GL11.glGenLists(1);

			// Start recording the new display list.
			GL11.glNewList(displayListHandle, GL11.GL_COMPILE);

			// End the recording of the current display list.

			GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
			GL11.glBegin(GL11.GL_QUADS);
			for (int y = 0; y < size.y; y++) {
				for (int x = 0; x < size.x; x++) {
					for (int z = 0; z < size.z; z++) {
						Material mat = data[y][x][z];
						if (mat != null) {
							if (!isCubeBlank(mat)) {
								renderCube(new Vector3f(x, y, z), step, mat);
							}
						}
					}
				}
			}
			GL11.glEnd();
			GL11.glPopMatrix();

			GL11.glEndList();
			System.out.println("Building...");
		} else {
			GL11.glCallList(displayListHandle);
		}

	}

	public void renderCube(Vector3f vec, float step, Material mat) {
		float x = vec.x;
		float y = vec.y;
		float z = vec.z;
		Vector2f tex = null;
		Material check = null;
		if (y + 1 < size.y) {
			check = data[(int) y + 1][(int) x][(int) z];
		} else {

			String key = (int) index.x + "," + ((int) index.y + 1) + "," + (int) index.z;
			Chunk chunkCheck = Application.chunks.get(key);

			if (chunkCheck != null) {
				Material matCheck = chunkCheck.data[0][(int) x][(int) z];

				if (matCheck != null) {
					boolean isBlank = isCubeBlank(matCheck);
					if (!isBlank) {
						check = matCheck;
					}
				}
			}
		}
		if (isCubeBlank(check)) {
			tex = mat.type.getVec()[0];
			if (tex != null) {
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
		}

		check = null;
		if (y - 1 >= 0) {
			check = data[(int) y - 1][(int) x][(int) z];
			System.out.println("check: " + check);
		} else {

			String key = (int) index.x + "," + ((int) index.y - 1) + "," + (int) index.z;
			Chunk chunkCheck = Application.chunks.get(key);

			if (chunkCheck != null) {
				Material matCheck = chunkCheck.data[(int) (size.y - 1)][(int) x][(int) z];
				if (matCheck != null) {
					boolean isBlank = isCubeBlank(matCheck);
					if (!isBlank) {
						check = matCheck;
					}
				}
			}
		}
		if (isCubeBlank(check)) {
			tex = mat.type.getVec()[1];
			if (tex != null) {
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
		}

		check = null;
		if (z - 1 >= 0) {
			check = data[(int) y][(int) x][(int) z - 1];
		} else {

			String key = (int) index.x + "," + (int) index.y + "," + (int) (index.z - 1);
			Chunk chunkCheck = Application.chunks.get(key);

			if (chunkCheck != null) {
				Material matCheck = chunkCheck.data[(int) y][(int) x][(int) (size.z - 1)];
				if (matCheck != null) {
					boolean isBlank = isCubeBlank(matCheck);
					if (!isBlank) {
						check = matCheck;
					}
				}
			}
		}
		if (isCubeBlank(check)) {
			tex = mat.type.getVec()[2];
			if (tex != null) {
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
		}

		check = null;
		if (z + 1 < size.z) {
			check = data[(int) y][(int) x][(int) z + 1];
		} else {

			String key = (int) index.x + "," + (int) index.y + "," + (int) (index.z + 1);
			Chunk chunkCheck = Application.chunks.get(key);

			if (chunkCheck != null) {
				Material matCheck = chunkCheck.data[(int) y][(int) x][0];
				if (matCheck != null) {
					boolean isBlank = isCubeBlank(matCheck);
					if (!isBlank) {
						check = matCheck;
					}
				}
			}
		}
		if (isCubeBlank(check)) {
			tex = mat.type.getVec()[3];
			if (tex != null) {
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
		}

		check = null;
		if (x + 1 < size.x) {
			check = data[(int) y][(int) x + 1][(int) z];
		} else {

			String key = (int) (index.x + 1) + "," + (int) index.y + "," + (int) index.z;
			Chunk chunkCheck = Application.chunks.get(key);

			if (chunkCheck != null) {
				Material matCheck = chunkCheck.data[(int) y][0][(int) z];
				if (matCheck != null) {
					boolean isBlank = isCubeBlank(matCheck);
					if (!isBlank) {
						check = matCheck;
					}
				}
			}
		}
		if (isCubeBlank(check)) {
			tex = mat.type.getVec()[4];
			if (tex != null) {
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
		}

		check = null;
		if (x - 1 >= 0) {
			check = data[(int) y][(int) x - 1][(int) z];
		} else {

			String key = (int) (index.x - 1) + "," + (int) index.y + "," + (int) index.z;
			Chunk chunkCheck = Application.chunks.get(key);

			if (chunkCheck != null) {
				Material matCheck = chunkCheck.data[(int) y][(int) (size.x - 1)][(int) z];
				if (matCheck != null) {
					boolean isBlank = isCubeBlank(matCheck);
					if (!isBlank) {
						check = matCheck;
					}
				}
			}
		}
		if (isCubeBlank(check)) {
			tex = mat.type.getVec()[5];
			if (tex != null) {
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
		}

	}
}
