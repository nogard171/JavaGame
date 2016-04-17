import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class OBJLoader {
	ArrayList<Vector3f> vectors = new ArrayList<Vector3f>();
	ArrayList<Vector4f> faces = new ArrayList<Vector4f>();
	ArrayList<Vector4f> textureFaces = new ArrayList<Vector4f>();
	ArrayList<Vector2f> textureVectors = new ArrayList<Vector2f>();
	String textureLocation = "";
	String mtlFile = "";
	String objectName = "";
	String textureFile = "";
	private Texture texture;
	public Texture getTexture()
	{
		return this.texture;
	}
	public ArrayList<Vector3f> getVectors()
	{
		return this.vectors;
	}
	public ArrayList<Vector4f> getFaces()
	{
		return this.faces;
	}
	public ArrayList<Vector4f> getTextureFaces()
	{
		return this.textureFaces;
	}
	public ArrayList<Vector2f> getTextureVectors()
	{
		return this.textureVectors;
	}

	public void loadData(String file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				if (line.startsWith("mtllib ")) {
					String[] data = line.split(" ");
					mtlFile = data[1];
					textureFile = loadMTL(mtlFile);

				}
				if (line.startsWith("o ")) {
					String[] data = line.split(" ");
					objectName = data[1];
				}
				if (line.startsWith("v ")) {
					String[] data = line.split(" ");
					Vector3f vec;
					if (data[1] == "") {
						vec = new Vector3f(Float.parseFloat(data[2]),
								Float.parseFloat(data[3]),
								Float.parseFloat(data[4]));
					} else {
						vec = new Vector3f(Float.parseFloat(data[1]),
								Float.parseFloat(data[2]),
								Float.parseFloat(data[3]));
					}

					vectors.add(vec);
				}
				if (line.startsWith("vt ")) {
					String[] data = line.split(" ");
					Vector2f vec = new Vector2f(Float.parseFloat(data[1]),
							Float.parseFloat(data[2]));
					textureVectors.add(vec);
				}
				if (line.startsWith("f ")) {
					String[] data = line.split(" ");
					Vector4f vec;
					if (data.length > 4) {
						vec = new Vector4f(
								Integer.parseInt(data[2].split("/")[0]),
								Integer.parseInt(data[3].split("/")[0]),
								Integer.parseInt(data[4].split("/")[0]),
								Integer.parseInt(data[1].split("/")[0]));
					} else {
						vec = new Vector4f(
								Integer.parseInt(data[2].split("/")[0]),
								Integer.parseInt(data[3].split("/")[0]),
								Integer.parseInt(data[3].split("/")[0]),
								Integer.parseInt(data[1].split("/")[0]));
					}
					faces.add(vec);

					if (data.length > 4) {
						vec = new Vector4f(
								Float.parseFloat(data[2].split("/")[1]),
								Float.parseFloat(data[3].split("/")[1]),
								Float.parseFloat(data[4].split("/")[1]),
								Float.parseFloat(data[1].split("/")[1]));
					} else {
						vec = new Vector4f(
								Float.parseFloat(data[2].split("/")[1]),
								Float.parseFloat(data[3].split("/")[1]),
								Float.parseFloat(data[3].split("/")[1]),
								Float.parseFloat(data[1].split("/")[1]));
					}
					textureFaces.add(vec);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public String loadMTL(String file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String textureTMP = "";
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				if (line.startsWith("map_Kd ")) {
					String[] data = line.split(" ");
					textureTMP = data[1];
					try {
						// load texture from PNG file
						texture = TextureLoader.getTexture("PNG",
								ResourceLoader.getResourceAsStream(textureTMP));

						System.out.println("Texture loaded: " + texture);
						System.out.println(">> Image width: "
								+ texture.getImageWidth());
						System.out.println(">> Image height: "
								+ texture.getImageHeight());
						System.out.println(">> Texture width: "
								+ texture.getTextureWidth());
						System.out.println(">> Texture height: "
								+ texture.getTextureHeight());
						System.out.println(">> Texture ID: "
								+ texture.getTextureID());
					} catch (IOException e) {
						// e.printStackTrace();
					}
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return textureTMP;
	}

}
