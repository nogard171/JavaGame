package Engine;

import static org.lwjgl.opengl.GL11.glCallList;

import org.lwjgl.opengl.GL11;

public class GLRenderer extends GLComponent {
	private int displayID = -1;
	public int getDisplayID() {
		return displayID;
	}

	public void setDisplayID(int displayID) {
		this.displayID = displayID;
	}

	private GLSize initSpriteSize = new GLSize(32, 32);
	public GLRenderer() {
		this.setName("renderer");
	}

	public void Run() {
		GLMaterial mat = (GLMaterial) super.getObject().getComponent("material");
		
		GLSize spriteSize = mat.getTextureSize();
		
		if (this.initSpriteSize != spriteSize) {
			this.displayID = -1;
			this.initSpriteSize = spriteSize;
		}
		if (this.displayID == -1) {
			int dlid = GL11.glGenLists(1);
			GL11.glNewList(dlid, GL11.GL_COMPILE);
			this.RenderQuad(spriteSize.getWidth(), spriteSize.getHeight(),0,0,1,1);
			GL11.glEndList();
			this.displayID = dlid;
		} else {
			glCallList(this.displayID);
		}
	}

	public void RenderQuad(int width, int height, float textureX, float textureY, float textureWidth, float textureHeight) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(textureX, textureHeight);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(textureX, textureY);
		GL11.glVertex2f(0, height);
		GL11.glTexCoord2f(textureWidth, textureY);
		GL11.glVertex2f(width, height);
		GL11.glTexCoord2f(textureWidth, textureHeight);
		GL11.glVertex2f(width, 0);
		GL11.glEnd();
	}

}
