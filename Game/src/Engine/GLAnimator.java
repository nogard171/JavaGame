package Engine;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GLAnimator extends GLComponent {
	private GLFrame[][] frames = new GLFrame[1][1];
	private Texture texture;
	private GLSize size = new GLSize(32, 64);
	private float frameX = 0;
	private float frameY = 0;
	private boolean loaded = false;
	private String textureFile = "";

	public GLAnimator() {
		this.setName("animator");
	}

	public GLAnimator(String filename) {
		this.textureFile = filename;
		this.setName("animator");
	}

	public void loadFrames() {
		if (this.textureFile != "") {
			try {
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(this.textureFile));
			} catch (IOException e) {
				e.printStackTrace();
			}

			GLMaterial mat = (GLMaterial) super.getObject().getComponent("material");
			mat.setTextureID(texture.getTextureID());
			mat.setTextureSize(size);

			int numXSprites = texture.getImageWidth() / this.size.getWidth();
			int numYSprites = texture.getImageHeight() / this.size.getHeight();
			System.out.println("coords:"+ numXSprites+","+numYSprites);
			if(numXSprites==0)
			{
				numXSprites = 1;
			}
			if(numYSprites==0)
			{
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
			} else {		
				int dlid = GL11.glGenLists(1);
				GL11.glNewList(dlid, GL11.GL_COMPILE);
				renderer.RenderQuad(texture.getImageWidth(), texture.getImageHeight() , 0, 0, 1, 1);
				GL11.glEndList();
				GLFrame frame = new GLFrame();
				frame.setDisplayID(dlid);
				frames[0][0] = frame;
				
				
			}
			loaded = true;
		}
		else
		{
			System.out.println("Failed to load");
		}
	}

	public void Run() {

		if (!this.loaded) {
			this.loadFrames();
		}
		if (this.loaded) {
			GLRenderer renderer = (GLRenderer) super.getObject().getComponent("renderer");

			if (renderer != null) {

				renderer.setDisplayID(this.getFrames((int) this.frameX, (int) this.frameY).getDisplayID());
			}
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
