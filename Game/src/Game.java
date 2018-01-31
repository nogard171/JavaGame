import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game extends GLDisplay {

	// int[][][] cubes;
	Vector3f size = new Vector3f(10, 5, 10);

	GLCube[][][] cubes;

	HashMap<String, GLTexture> textures = new HashMap<String, GLTexture>();

	@Override
	public void Setup() {
		super.Setup();

		shader = new GLShader("basic.vert", "basic.frag");
		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("resources/textures/tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cubes = new GLCube[(int) size.x][100][(int) size.z];

		for (int x = 0; x < size.x; x++) {
			for (int z = 0; z < size.z; z++) {
				for (int y = 0; y < size.y; y++) {
					GLCube cube = new GLCube(x, y, z);

					if (y < size.y - 1) {
						cube.type = "STONE";
					}

					if (y == size.y) {
						cube.type = "BLANK";
					}
					cubes[x][y][z] = cube;
				}
			}
		}

		level = (int) size.y;

		String[] textureName = { "GRASS", "DIRT", "UNKNOWN", "STONE" };

		ArrayList<GLTexture> texturesTemp = new ArrayList<GLTexture>();

		texturesTemp.add(new GLTexture("GRASS", new Vector2f(0, 0)));
		texturesTemp.add(new GLTexture("DIRT", new Vector2f(1, 0)));
		texturesTemp.add(new GLTexture("UNKNOWN", new Vector2f(0, 1)));
		texturesTemp.add(new GLTexture("STONE", new Vector2f(1, 1)));

		for (int t = 0; t < texturesTemp.size(); t++) {
			GLTexture LocalTexture = texturesTemp.get(t);

			LocalTexture.dl = GL11.glGenLists(1);

			// Start recording the new display list.
			GL11.glNewList(LocalTexture.dl, GL11.GL_COMPILE);

			// RenderCube(texture.top, texture.other);
			RenderCube(LocalTexture.textureCoords, LocalTexture.size);

			// End the recording of the current display list.
			GL11.glEndList();

			textures.put(LocalTexture.name, LocalTexture);
		}

		if (texture != null) {
			shader.sendTexture("myTexture", texture.getTextureID());
		}

		checkSurroundings();
	}

	Texture texture;

	GLShader shader;

	@Override
	public void Update(float delta) {
		super.Update(delta);

		float speed = (delta + 1) * 0.5f;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			view.x -= speed;
			viewChanged = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			view.x += speed;
			viewChanged = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			view.y += speed;
			viewChanged = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			view.y -= speed;
			viewChanged = true;
		}
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					super.close = true;
				}
			}
		}
		if (viewChanged) {
			checkSurroundings();
		}
	}

	boolean viewChanged = false;

	public void checkSurroundings() {

		cubesToRender.clear();
		for (int x = 0; x < size.x; x++) {
			for (int z = 0; z < size.z; z++) {
				int lowest = level - 5;
				if (lowest < 0) {
					lowest = 0;
				}
				lowest = 0;
				for (int y = lowest; y < level; y++) {
					GLCube cube = cubes[x][y][z];
					if (cube != null) {
						Vector3f count = getSurrounding(x, y, z);
						if (count.getY() > 0) {
							cube.visible = false;
						}
						if (view.contains(cube.position2D)) {

							if (!count.equals(new Vector3f(1, 1, 1))) {
								cubesToRender.add(cube);
							}
						}
					}
				}
			}
		}
	}

	public Vector3f getSurrounding(int x, int y, int z) {
		Vector3f count = new Vector3f(0, 0, 0);

		if (x + 1 < size.x) {
			if (cubes[x + 1][y][z] != null) {
				count.x = 1;
			}
		}
		if (z + 1 < size.z) {
			if (cubes[x][y][z + 1] != null) {
				count.z = 1;
			}
		}

		if (y + 1 < level) {
			if (cubes[x][y + 1][z] != null) {
				count.y = 1;
			}
		}

		return count;
	}

	ArrayList<GLCube> cubesToRender = new ArrayList<GLCube>();

	int level = 1;
	boolean shaderRan = false;

	@Override
	public void Render() {
		super.Render();

		shader.Run();
		if (cubesToRender.size() > 0) {
			// if (!shaderRan) {
			float[] colorData = { 1, 1, 1, 1 };
			shader.sendUniform4f("vertColor", colorData);

			// shaderRan = true;
			// }
			float[] viewPosition = { (float) (-view.getX() - 64), (float) (-view.getY() - 64) };

			shader.sendUniform2f("view", viewPosition);

			for (GLCube cube : cubesToRender) {

				float[] position = { (float) (cube.position.getX()), (float) (cube.position.getY()),
						(float) cube.position.getZ() };

				shader.sendUniform3f("position", position);

				if (cube.type != "BLANK") {
					GLTexture tempTexture = textures.get(cube.type);

					if (!cube.visible) {
						tempTexture = textures.get("UNKNOWN");
					}

					GL11.glCallList(tempTexture.dl);
					/*
					if (!cube.visible) {
						 tempTexture =  textures.get("UNKNOWN");
					}
					
					RenderCube(tempTexture.textureCoords,tempTexture.size);*/
				}
			}

		}
	}

	public void RenderCube(Vector2f texture, Vector2f textureSize) {
		GL11.glBegin(GL11.GL_QUADS);
		// top
		GL11.glTexCoord2f(0.5f * (texture.x), 0.5f * (texture.y + textureSize.y));
		GL11.glVertex2f(0, 0);

		GL11.glTexCoord2f(0.5f * (texture.x + textureSize.x), 0.5f * (texture.y + textureSize.y));
		GL11.glVertex2f(64 * textureSize.x, 0);

		GL11.glTexCoord2f(0.5f * (texture.x + textureSize.x), 0.5f * (texture.y));
		GL11.glVertex2f(64 * textureSize.x, 64 * textureSize.y);

		GL11.glTexCoord2f(0.5f * (texture.x), 0.5f * (texture.y));
		GL11.glVertex2f(0, 64 * textureSize.y);

		GL11.glEnd();
	}

	@Override
	public void Destroy() {

		super.Destroy();
	}

}
