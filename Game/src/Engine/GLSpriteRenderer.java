package Engine;

import org.lwjgl.opengl.GL11;

public class GLSpriteRenderer extends GLComponent {
	public GLSpriteRenderer() {
		this.setName("spriterenderer");
	}

	public void Render() {
		GLMaterial mat = (GLMaterial) this.getObject().getComponent("material");
		if (mat != null) {
			if (mat.getTextureID() != -1) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, mat.getTextureID());
			}
		}
	}

	public void Unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, -1);
	}
}
