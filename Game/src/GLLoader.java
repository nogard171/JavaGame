import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class GLLoader {
	public GLRawQuad getRaw(String filename) {
		ArrayList<String> lines = getFileLines(filename);
		ArrayList<Vector2f> vectors = new ArrayList<Vector2f>();
		ArrayList<Byte> indices = new ArrayList<Byte>();

		for (int i = 0; i < lines.size(); i++) {
			String[] data = lines.get(i).split(" ");

			String type = data[0];

			if (type.startsWith("v") && data.length >= 2) {
				vectors.add(new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2])));
			}
			if (type.startsWith("i") && data.length >= 3) {
				indices.add(Byte.parseByte(data[1]));
				indices.add(Byte.parseByte(data[2]));
				indices.add(Byte.parseByte(data[3]));
			}
		}
		GLRawQuad raw = new GLRawQuad();
		Vector2f[] newVectors = new Vector2f[vectors.size()];
		byte[] newIndices = new byte[indices.size()];
		for (int v = 0; v < newVectors.length; v++) {
			newVectors[v] = vectors.get(v);
		}
		for (int i = 0; i < newIndices.length; i++) {
			newIndices[i] = indices.get(i);
		}
		raw.indices = newIndices;
		raw.vectors = newVectors;
		//
		return raw;
	}

	public int getDisplayList(GLRawQuad raw) {
		int dlid = GL11.glGenLists(1);
		GL11.glNewList(dlid, GL11.GL_COMPILE);
		renderRawQuad(raw);
		GL11.glEndList();
		return dlid;
	}

	public void renderRawQuad(GLRawQuad raw) {
		float x,y;
		for (byte indice : raw.indices) {
			Vector2f vec = raw.vectors[indice];
			Vector2f textureVec = raw.textureVectors[indice];
			System.out.println(textureVec.getX()+","+ textureVec.getY());
			GL11.glTexCoord2f(textureVec.getX(), textureVec.getY());
			GL11.glVertex2f(vec.getX(), vec.getY());
		}
	}

	public GLQuad getQuad(String filename) {

		GLQuad quad = new GLQuad();

		String rawFilename = "";

		ArrayList<Vector2f> textureVectors = new ArrayList<Vector2f>();
		
		ArrayList<String> lines = getFileLines(filename);
		for (int i = 0; i < lines.size(); i++) {
			String[] data = lines.get(i).split(" ");

			String type = data[0];

			if (type.startsWith("raw")) {
				rawFilename = data[1];
			}

			if (type.startsWith("tv") && data.length >= 2) {
				textureVectors.add(new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2])));
			}
		}
		Vector2f[] newTextureVectors = new Vector2f[textureVectors.size()];
		for (int v = 0; v < newTextureVectors.length; v++) {
			newTextureVectors[v] = textureVectors.get(v);
		}
		if (rawFilename != "") {
			GLRawQuad raw = getRaw(rawFilename);
			raw.textureVectors = newTextureVectors;
			quad.displayList = getDisplayList(raw);
		}
		return quad;
	}

	public ArrayList<String> getFileLines(String filename) {
		ArrayList<String> lines = new ArrayList<String>();

		String line;
		try (InputStream fis = new FileInputStream(filename);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);) {
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lines;
	}
}
