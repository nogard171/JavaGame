package engine;

import static org.lwjgl.opengl.GL11.glCallList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class GLWindow extends GLComponent {
	int dlWindow = -1;
	GLSize frameDimensions = new GLSize(5,5);
	int[][] frameInts;
	int[] frames = {};

	public GLWindow() {
		this.setName("window");
	}

	public void makeWindowInts() {
		frameInts = new int[this.frameDimensions.getHeight()][this.frameDimensions.getWidth()];
		int index = 0;
		for (int y = 0; y < this.frameDimensions.getHeight(); y++) {
			for (int x = 0; x < this.frameDimensions.getWidth(); x++) {
				frameInts[y][x] = index;
				System.out.println("X:" + x + "," + (this.frameDimensions.getWidth() - 2) + "(" + index + ")");
				if ((x == 0 || x == this.frameDimensions.getWidth() - 2 || x == this.frameDimensions.getWidth() - 1)
						&& (y == 0 || y >= this.frameDimensions.getHeight() - 2)) {
					index++;
				} else if ((x == 0 || x == this.frameDimensions.getWidth() - 2)) {
					index++;
				} else if (x == this.frameDimensions.getWidth() - 1) {
					index -= 2;
				}
			}
		}
	}

	public void loadWindow() {
		GLMaterial mat = (GLMaterial) this.getObject().getComponent("material");
		mat.setFrameSize(new GLSize(32, 32));
		if (mat != null) {
			float textureWidthStep = 1 / (float) 3;
			float textureHeightStep = 1 / (float) 3;
			GLRenderer renderer = (GLRenderer) this.getObject().getComponent("renderer");
			frames = new int[((int) frameDimensions.getWidth() * (int) frameDimensions.getHeight())];
			if (renderer != null) {
				int index = 0;
				for (int y = 0; y < 3; y++) {
					for (int x = 0; x < 3; x++) {
						int dlid = GL11.glGenLists(1);
						GL11.glNewList(dlid, GL11.GL_COMPILE);
						renderer.RenderQuad(mat.getFrameSize().getWidth(), mat.getFrameSize().getHeight(),
								x * textureWidthStep, (y + 1) * textureHeightStep, (x + 1) * textureWidthStep,
								y * textureHeightStep);
						GL11.glEndList();
						frames[index] = dlid;
						index++;
					}
				}
				this.makeWindowInts();
				int dlid = GL11.glGenLists(1);
				GL11.glNewList(dlid, GL11.GL_COMPILE);
				for (int y = 0; y < frameInts.length; y++) {
					for (int x = 0; x < frameInts[0].length; x++) {
						GL11.glPushMatrix();
						GL11.glTranslatef(x * 32, y * 32, 0);
						int newIndex = frameInts[y][x];
						glCallList(this.frames[newIndex]);
						GL11.glPopMatrix();
					}
				}
				GL11.glEndList();
				this.dlWindow = dlid;
			}
		}
	}

	public void Run() {
		GLRenderer renderer = (GLRenderer) this.getObject().getComponent("renderer");
		if (renderer != null) {
			if (this.dlWindow == -1) {
				this.loadWindow();
			}
			renderer.setDisplayID(this.dlWindow);
		}
	}
}
