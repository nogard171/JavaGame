package Engine;

import static org.lwjgl.opengl.GL11.glCallList;

import org.lwjgl.opengl.GL11;

public class GLRenderer extends GLComponent {
	private int displayID = -1;
	private GLSize spriteSize = new GLSize(32, 32);
	private GLSize initSpriteSize = new GLSize(32, 32);

	public GLRenderer() {
		this.setName("spriterenderer");
	}

	public void Run() {
		if (this.initSpriteSize != this.spriteSize) {
			this.displayID = -1;
			this.initSpriteSize = this.spriteSize;
		}
		if (this.displayID == -1) {
			int dlid = GL11.glGenLists(1);
			GL11.glNewList(dlid, GL11.GL_COMPILE);
			this.RenderQuad(this.spriteSize.getWidth(), this.spriteSize.getHeight());
			GL11.glEndList();
			this.displayID = dlid;
		} else {
			glCallList(this.displayID);
		}
	}

	public void RenderQuad(int width, int height) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(0, height);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(width, height);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(width, 0);
		GL11.glEnd();
	}

}
