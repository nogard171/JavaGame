package engine;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GLAnimator extends GLComponent {
	private GLFrame[][] frames = new GLFrame[1][1];
	private Texture texture;
	private GLSize size = new GLSize(32, 32);

	private float frameX = 0;
	private float frameY = 0;
	private boolean loaded = false;

	public GLSize getSize() {
		return size;
	}

	public void setSize(GLSize size) {
		this.size = size;
	}

	public GLAnimator() {
		this.setName("animator");
	}

	public int getNumFramesX() {
		return frames.length;
	}

	public int getNumFramesY() {
		return frames[0].length;
	}

	public void loadFrames() {
		if (super.getObject() != null) {
			GLMaterial mat = (GLMaterial) super.getObject().getComponent("material");
			if (mat != null) {
				// mat.setTextureSize(size);
				mat.setFrameSize(size);
				GLSize textureSize = mat.getTextureSize();
				GLSize frameSize = mat.getFrameSize();

				int numXSprites = textureSize.getWidth() / frameSize.getWidth();
				int numYSprites = textureSize.getHeight() / frameSize.getHeight();
				if (numXSprites == 0) {
					numXSprites = 1;
				}
				if (numYSprites == 0) {
					numYSprites = 1;
				}
				frames = new GLFrame[numXSprites][numYSprites];
				GLRenderer renderer = (GLRenderer) super.getObject().getComponent("renderer");
				if (numXSprites > 1 || numYSprites > 1) {
					float xStep = 1 / (float) numXSprites;
					float yStep = 1 / (float) numYSprites;
					if (renderer != null) {
						for (int x = 0; x < numXSprites; x++) {
							for (int y = 0; y < numYSprites; y++) {
								int dlid = GL11.glGenLists(1);
								GL11.glNewList(dlid, GL11.GL_COMPILE);
								renderer.RenderQuad(mat.getFrameSize().getWidth(), mat.getFrameSize().getHeight(),
										(float) (x * xStep), (float) (y * yStep), (float) ((x + 1) * xStep),
										(float) ((y + 1) * yStep));
								GL11.glEndList();
								GLFrame frame = new GLFrame();
								frame.setDisplayID(dlid);
								frames[x][y] = frame;
							}
						}
					}
				} else {
					int dlid = GL11.glGenLists(1);
					GL11.glNewList(dlid, GL11.GL_COMPILE);
					renderer.RenderQuad(texture.getImageWidth(), texture.getImageHeight(), 0, 0, 1, 1);
					GL11.glEndList();
					GLFrame frame = new GLFrame();
					frame.setDisplayID(dlid);
					frames[0][0] = frame;
				}
				loaded = true;
			} else {
				System.out.println("No Material for GLAnimator");
			}
		} else {
			System.out.println("No Object for GLAnimator");
		}
	}

	public void Run() {

		GLRenderer renderer = (GLRenderer) super.getObject().getComponent("renderer");
		if (renderer != null) {
			if (!this.loaded) {
				this.loadFrames();
			}
			if (this.loaded) {
				renderer.setDisplayID(this.getFrames((int) this.frameX, (int) this.frameY).getDisplayID());
			}
		}
	}

	public void setFrameY(float y) {
		this.frameY = y;
	}

	public void setFrameX(float x) {
		System.out.println(x);
		this.frameX = x;
	}

	private GLFrame getFrames(int frameX, int frameY) {
		
		if(frameX>this.frames.length-1)
		{
			frameX = this.frames.length-1;
		}
		
		return this.frames[frameX][frameY];
	}
}
