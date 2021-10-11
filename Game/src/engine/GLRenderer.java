package engine;

import static org.lwjgl.opengl.GL11.glCallList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class GLRenderer extends GLComponent {
	public int displayID = -1;

	private GLSize initSpriteSize;

	public GLRenderer() {
		this.setName("renderer");
		initSpriteSize = new GLSize(32, 32);
		generateDisplayList(new GLSize(32, 32));
	};

	public int getDisplayID() {
		return displayID;
	}

	public void setDisplayID(int displayID) {
		this.displayID = displayID;
	}

	private void generateDisplayList(GLSize spriteSize) {
		if (this.displayID == -1) {
			int dlid = GL11.glGenLists(1);
			GL11.glNewList(dlid, GL11.GL_COMPILE);

			this.RenderQuad(spriteSize.getWidth(), spriteSize.getHeight(), 0, 0, 1, 1);

			GL11.glEndList();
			this.displayID = dlid;
		}
	}

	public void Run() {
		GLMaterial mat = (GLMaterial) super.getObject().getComponent("material");
		GLSize spriteSize = mat.getTextureSize();
		if (this.initSpriteSize != spriteSize) {
			this.displayID = -1;
			this.initSpriteSize = spriteSize;
			this.generateDisplayList(initSpriteSize);
		}
		if (this.displayID != -1) {
			glCallList(this.displayID);
		}
	}

	public void RenderQuad(int width, int height, float textureX, float textureY, float textureWidth,
			float textureHeight) {
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