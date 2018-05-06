import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class GLLoader {
	public GLModel loadModel(String filename) {
		GLModelData data = new GLModelData();

		ArrayList<Vector2f> newVectors = new ArrayList<Vector2f>();
		ArrayList<Byte> newIndices = new ArrayList<Byte>();

		ArrayList<Vector2f> newTextureVectors = new ArrayList<Vector2f>();
		ArrayList<Byte> newTextureIndices = new ArrayList<Byte>();

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("resources/data/objects/" + filename));
			String line = reader.readLine();
			while (line != null) {

				String[] lineData = line.split(" ");
				String header = lineData[0].toLowerCase();
				if (header.equals("v")) {
					if (lineData.length == 3) {
						float x = Float.parseFloat(lineData[1]);
						float y = Float.parseFloat(lineData[2]);

						newVectors.add(new Vector2f(x, y));
					}
				}
				if (header.equals("i")) {
					if (lineData.length == 4) {
						newIndices.add(Byte.parseByte(lineData[1]));
						newIndices.add(Byte.parseByte(lineData[2]));
						newIndices.add(Byte.parseByte(lineData[3]));
					}
				}
				if (header.equals("tv")) {
					if (lineData.length == 3) {
						float x = Float.parseFloat(lineData[1]);
						float y = Float.parseFloat(lineData[2]);
						newTextureVectors.add(new Vector2f(new GLMath().textureXVectorToFloat(x),
								new GLMath().textureYVectorToFloat(y)));
					}
				}
				if (header.equals("ti")) {
					if (lineData.length == 4) {
						newTextureIndices.add(Byte.parseByte(lineData[1]));
						newTextureIndices.add(Byte.parseByte(lineData[2]));
						newTextureIndices.add(Byte.parseByte(lineData[3]));
					}
				}
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Vector2f[] vectors = new Vector2f[newVectors.size()];
		for (int v = 0; v < newVectors.size(); v++) {
			vectors[v] = newVectors.get(v);
		}
		data.vectors = vectors;

		Byte[] indices = new Byte[newIndices.size()];
		for (int i = 0; i < newIndices.size(); i++) {
			indices[i] = newIndices.get(i);
		}
		data.indices = indices;

		Vector2f[] textureVectors = new Vector2f[newTextureVectors.size()];
		for (int v = 0; v < newTextureVectors.size(); v++) {
			textureVectors[v] = newTextureVectors.get(v);
		}
		data.textureVectors = textureVectors;

		Byte[] textureIndices = new Byte[newTextureIndices.size()];
		for (int i = 0; i < newTextureIndices.size(); i++) {
			textureIndices[i] = newTextureIndices.get(i);
		}
		data.textureIndices = textureIndices;

		GLModel model = new GLModel();

		model.displayListID = this.getDisplayList(data);

		return model;
	}

	private int getDisplayList(GLModelData data) {
		int dl = GL11.glGenLists(1);
		GL11.glNewList(dl, GL11.GL_COMPILE);

		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int i = 0; i < data.indices.length; i++) {
			Byte indice = data.indices[i];
			Vector2f vector = data.vectors[indice];

			Byte textureIndice = data.textureIndices[i];
			Vector2f textureVector = data.textureVectors[textureIndice];
			GL11.glTexCoord2f(textureVector.x, textureVector.y);
			GL11.glVertex2f(vector.x, vector.y);
		}
		GL11.glEnd();
		GL11.glEndList();
		return dl;
	}

}
