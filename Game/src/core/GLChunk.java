package core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class GLChunk {
	private int dlId = -1;
	private GLObject[][][] objects;

	public GLChunk() {
		objects = new GLObject[16][16][16];
		int xCount = objects.length;
		int zCount = objects[0].length;
		int yCount = objects[0][0].length;
		for (int x = 0; x < xCount; x++) {
			for (int z = 0; z < zCount; z++) {
				for (int y = 0; y < yCount; y++) {
					if (x == 3 && z == 3) {
						objects[x][z][y] = new GLObject(GLType.GRASS);
					} else {
						objects[x][z][y] = new GLObject(GLType.BLANK);
					}

				}
			}
		}
		updateDisplayList();
	}

	public void updateDisplayList() {

		dlId = GL11.glGenLists(1);
		GL11.glNewList(dlId, GL11.GL_COMPILE);

		GL11.glBegin(GL11.GL_QUADS);
		int xCount = objects.length;
		int zCount = objects[0].length;
		int yCount = objects[0][0].length;
		for (int x = 0; x < xCount; x++) {
			for (int z = 0; z < zCount; z++) {
				for (int y = 0; y < yCount; y++) {
					GLObject obj = objects[x][z][y];
					if (obj != null) {
						Color color = new Color(128, 128, 128);
						if (obj.getType() == GLType.BLANK) {
						} else if (obj.getType() == GLType.GRASS) {
							color = new Color(128, 255, 128);
						}
						renderObject(color, x, y, z);
					}
				}
			}
		}
		GL11.glEnd();
		GL11.glEndList();
	}

	private void renderObject(Color color, int x, int y, int z) {

		int posX = (x - z) * 33;
		int posY = y * 33;
		int posZ = ((z + x) * 17) - posY;

		GL11.glColor3f((float) color.getRed() / (float) 255, (float) color.getGreen() / (float) 255,
				(float) color.getBlue() / (float) 255);

		GL11.glVertex2f(posX + 32, posZ);
		GL11.glVertex2f(posX + 64, posZ + 16);
		GL11.glVertex2f(posX + 32, posZ + 32);
		GL11.glVertex2f(posX, posZ + 16);

		GL11.glColor3f(((float) color.getRed() / (float) 255) * 0.75f, ((float) color.getGreen() / (float) 255) * 0.75f,
				((float) color.getBlue() / (float) 255) * 0.75f);

		GL11.glVertex2f(posX, posZ + 16);
		GL11.glVertex2f(posX + 32, posZ + 32);
		GL11.glVertex2f(posX + 32, posZ + 64);
		GL11.glVertex2f(posX, posZ + 48);

		GL11.glColor3f(((float) color.getRed() / (float) 255) * 0.5f, ((float) color.getGreen() / (float) 255) * 0.5f,
				((float) color.getBlue() / (float) 255) * 0.5f);

		GL11.glVertex2f(posX + 32, posZ + 32);
		GL11.glVertex2f(posX + 64, posZ + 16);
		GL11.glVertex2f(posX + 64, posZ + 48);
		GL11.glVertex2f(posX + 32, posZ + 64);
	}

	public void render() {
		GL11.glCallList(this.dlId);
	}
}
