package engine;

import java.awt.Point;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import classes.GLChunk;
import classes.GLMaterialData;
import classes.GLModel;
import classes.GLModelData;
import classes.GLObject;
import classes.GLPosition;
import classes.GLSpriteData;
import game.GLData;

public class GLRenderer {

	public static void RenderChunk(GLChunk chunk) {
		GLShader shader = new GLData().shader;
		if (shader != null) {
			if (chunk.chunkDisplayList == -1) {

				chunk.chunkDisplayList = GL11.glGenLists(1);

				System.out.println("Count: " + chunk.objects.size());
				
				GL11.glNewList(chunk.chunkDisplayList, GL11.GL_COMPILE);
				for (GLObject obj : chunk.objects) {
					float[] position = { obj.position.getX(), obj.position.getY() };
					//System.out.println("X:"+obj.position.getX()+"\nY:"+ obj.position.getY());
					GLModel model = new GLData().models.get(obj.type);

					shader.sendUniform2f("position", position);
					shader.sendTexture("myTexture", model.textureID);
					GL11.glCallList(model.displayList);
				}
				GL11.glEndList();

			} else {
				GL11.glCallList(chunk.chunkDisplayList);
			}
		}
	}

	public static void RenderSprite(int width, int height) {
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2i(0, 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2i(width, 0);

		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2i(width, height);

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2i(0, height);

		GL11.glEnd();
	}

	public static void RenderSubSprite(GLSpriteData SpriteData) {

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(SpriteData.texturePosition.x, SpriteData.texturePosition.y);
		GL11.glVertex2f(0, 0);

		GL11.glTexCoord2f(SpriteData.texturePosition.x + SpriteData.textureSize.width, SpriteData.texturePosition.y);
		GL11.glVertex2f(SpriteData.size.width, 0);

		GL11.glTexCoord2f(SpriteData.texturePosition.x + SpriteData.textureSize.width,
				SpriteData.texturePosition.y + SpriteData.textureSize.height);
		GL11.glVertex2f(SpriteData.size.width, SpriteData.size.height);

		GL11.glTexCoord2f(SpriteData.texturePosition.x, SpriteData.texturePosition.y + SpriteData.textureSize.height);
		GL11.glVertex2f(0, SpriteData.size.height);

		GL11.glEnd();
	}

	public static void RenderModel(GLModelData modelData, GLMaterialData materialData) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int i = 0; i < modelData.indices.size(); i++) {
			Byte indice = modelData.indices.get(i);
			Byte textureIndice = materialData.textureIndices.get(i);
			GLPosition vector = modelData.vectors.get(indice);
			GLPosition textureVector = materialData.textureVectors.get(textureIndice);

			GL11.glTexCoord2f(textureVector.x, textureVector.y);
			GL11.glVertex2f(vector.x, vector.y);
		}
		GL11.glEnd();
	}
}
