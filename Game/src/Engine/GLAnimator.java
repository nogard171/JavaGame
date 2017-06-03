package Engine;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GLAnimator extends GLComponent {
	GLFrame[][] frames = new GLFrame[1][1];
	Texture texture;
	GLSize size = new GLSize(32, 64);
	float frameX = 0;
	float frameY = 0;
	boolean loaded = false;

	public GLAnimator() {
		this.setName("animator");
	}

	public void loadFrames(String filename) {
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/" + filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		GLMaterial mat = (GLMaterial) super.getObject().getComponent("material");
		mat.setTextureID(texture.getTextureID());
		mat.setTextureSize(size);

		int numXSprites = texture.getImageWidth() / this.size.getWidth();
		int numYSprites = texture.getImageHeight() / this.size.getHeight();

		float xStep = 1 / (float) numXSprites;
		float yStep = 1 / (float) numYSprites;
		frames = new GLFrame[numXSprites][numYSprites];

		GLRenderer renderer = (GLRenderer) super.getObject().getComponent("renderer");
		if (renderer != null) {
			for (int x = 0; x < numXSprites; x++) {
				for (int y = 0; y < numYSprites; y++) {
					int dlid = GL11.glGenLists(1);
					GL11.glNewList(dlid, GL11.GL_COMPILE);
					renderer.RenderQuad(mat.getTextureSize().getWidth(), mat.getTextureSize().getHeight(),
							(float) (x * xStep), (float) (y * yStep), (float) ((x + 1) * xStep),
							(float) ((y + 1) * yStep));
					GL11.glEndList();
					GLFrame frame = new GLFrame();
					frame.setDisplayID(dlid);
					frames[x][y] = frame;
				}
			}

		}
		loaded = true;
	}

	public void Run() {

		if (!this.loaded) {
			this.loadFrames("textures/guy.png");
		}

		GLRenderer renderer = (GLRenderer) super.getObject().getComponent("renderer");

		if (renderer != null) {

			renderer.setDisplayID(this.getFrames((int) this.frameX, (int) this.frameY).getDisplayID());
		}
	}

	public void setFrameY(int y) {
		this.frameY = y;
	}

	public void setFrameX(int x) {
		this.frameX = x;
	}

	private GLFrame getFrames(int frameX, int frameY) {
		// TODO Auto-generated method stub
		return this.frames[frameX][frameY];
	}
}
