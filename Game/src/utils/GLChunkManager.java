package utils;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import classes.GLChunk;
import classes.GLModelData;
import components.GLShader;

public class GLChunkManager {
	public static ArrayList<GLChunk> chunks = new ArrayList<GLChunk>();
	public static ArrayList<GLChunk> chunksInView = new ArrayList<GLChunk>();

	public static void UpdateChunks() {
		// chunksInView.clear();
		for (GLChunk chunk : chunks) {
			if (ChunkInView(chunk) && !chunksInView.contains(chunk)) {
				ManageChunk(chunk);
				chunksInView.add(chunk);
			} else {
				chunksInView.remove(chunk);
			}
		}
	}

	public static void RenderChunks(GLShader shader) {

		for (GLChunk chunk : chunksInView) {
			RenderChunk(shader, chunk);
		}
	}

	private static boolean ChunkInView(GLChunk chunk) {
		boolean inView = false;
		if (chunk.getPosition().GetX() + (32 * 16) >= 0 && chunk.getPosition().GetX() <= GLHandler.GetDisplayWidth()
				&& chunk.getPosition().GetY() + (32 * 16) >= 0
				&& chunk.getPosition().GetY() <= GLHandler.GetDisplayHeight()) {
			inView = true;
		}

		return inView;
	}

	private static void ManageChunk(GLChunk chunk) {
		chunk.ClearModelsToRender();
		GLModelData[][][] models = chunk.GetModels();
		int xCount = models.length;
		int yCount = models[0].length;
		int zCount = models[0][0].length;
		for (int x = 0; x < xCount; x++) {
			for (int y = 0; y < yCount; y++) {
				for (int z = 0; z < zCount; z++) {
					GLModelData model = models[x][y][z];
					if (model != null) {
						chunk.AddModel(model);
						break;
					}
				}
			}
		}
	}

	private static void RenderChunk(GLShader shader, GLChunk chunk) {
		int dl = chunk.GetDisplayList();
		if (dl == -1) {
			dl = GL11.glGenLists(1);
			GL11.glNewList(dl, GL11.GL_COMPILE);
			float[] chunkPosition = { chunk.getPosition().GetX(), chunk.getPosition().GetY(), 0 };
			shader.sendUniform3f("chunkPosition", chunkPosition);
			ArrayList<GLModelData> models = chunk.GetModelsToRender();
			for (GLModelData model : models) {
				if (model != null) {
					float[] color = { 1, 0, 0, 1 };
					shader.sendUniform4f("vertColor", color);
					float[] position = { model.getPosition().GetX(), model.getPosition().GetY(), 0 };
					shader.sendUniform3f("position", position);
					GLHandler.RenderModel(model);
				}
			}
			GL11.glEndList();
			chunk.SetDisplayList(dl);
		} else {
			GL11.glCallList(dl);
		}
	}
}
