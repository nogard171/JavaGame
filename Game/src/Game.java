import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
	Vector3f size = new Vector3f(100, 10, 100);

	GLCube[][][] cubes;

	HashMap<String, Integer> textures = new HashMap<String, Integer>();

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
		cubes = new GLCube[(int) size.x][(int) size.y][(int) size.z];

		for (int x = 0; x < size.x; x++) {
			for (int y = 0; y < size.y; y++) {
				for (int z = 0; z < size.z; z++) {
					cubes[x][y][z] = new GLCube(x, y, z);
				}
			}
		}

		level = 12;

		String[] textureName = { "GRASS", "DIRT", "UNKNOWN" };
		Vector2f[] textureVectors = { new Vector2f(0, 0), new Vector2f(1, 0),

				new Vector2f(0, 1), new Vector2f(0, 1),

				new Vector2f(1, 0), new Vector2f(1, 0) };

		for (int t = 0; t < textureName.length; t++) {

			int dl = GL11.glGenLists(1);

			// Start recording the new display list.
			GL11.glNewList(dl, GL11.GL_COMPILE);

			// Render a single cube
			// this.RenderCube();

			RenderCube(textureVectors[(t)], textureVectors[(t + 1)]);

			// End the recording of the current display list.
			GL11.glEndList();

			textures.put(textureName[t], dl);
		}

		checkSurroundings();
	}

	Texture texture;

	GLShader shader;

	@Override
	public void Update(float delta) {
		super.Update(delta);

		float speed = delta * 0.5f;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			view.x -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			view.x += speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			view.y += speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			view.y -= speed;
		}
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_UP && level < size.y) {
					level++;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && level >= 1) {
					level--;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					super.close = true;
				}
			}
		}
		checkSurroundings();
	}

	public void checkSurroundings() {

		cubesToRender.clear();
		for (int x = 0; x < size.x; x++) {
			for (int y = 0; y < size.y; y++) {
				for (int z = 0; z < size.z; z++) {
					GLCube cube = cubes[x][y][z];
					if (view.contains(cube.position2D)) {
						Vector3f count = getSurrounding(x, y, z);
						if (count.getY() > 0) {
							cube.visible = false;
						}
						if (!count.equals(new Vector3f(1, 1, 1))) {
							cubesToRender.add(cube);
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

		if (y + 1 < size.y) {
			if (cubes[x][y + 1][z] != null) {
				count.y = 1;
			}
		}

		return count;
	}

	ArrayList<GLCube> cubesToRender = new ArrayList<GLCube>();

	int level = 1;

	@Override
	public void Render() {
		super.Render();

		shader.Run();

		float[] colorData = { 1, 1, 1, 1 };
		shader.sendUniform4f("vertColor", colorData);
		if (texture != null) {
			shader.sendTexture("myTexture", texture.getTextureID());
		}

		for (GLCube cube : cubesToRender) {

			float[] position = { (float) (cube.position.getX() - 64 - view.getX()),
					(float) ((-cube.position.getZ() + cube.position.getY()) - view.getY() - 64), 0 };

			shader.sendUniform3f("position", position);
			int dl = this.textures.get(cube.type);

			if (!cube.visible) {
				dl = textures.get("UNKNOWN");
			}

			GL11.glCallList(dl);
		}
	}

	public void RenderCube() {
		GL11.glBegin(GL11.GL_QUADS);

		// left
		GL11.glTexCoord2f(0.5f, 0);
		GL11.glVertex2f(32, 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(0, 16);

		GL11.glTexCoord2f(1, 0.5f);
		GL11.glVertex2f(0, 48);

		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex2f(32, 32);

		// top
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(32, 32);

		GL11.glTexCoord2f(0.5f, 0);
		GL11.glVertex2f(64, 48);

		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex2f(32, 64);

		GL11.glTexCoord2f(0, 0.5f);
		GL11.glVertex2f(0, 48);

		// right
		GL11.glTexCoord2f(0.5f, 0);
		GL11.glVertex2f(32, 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(64, 16);

		GL11.glTexCoord2f(1, 0.5f);
		GL11.glVertex2f(64, 48);

		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex2f(32, 32);
		GL11.glEnd();
	}

	public void RenderCube(Vector2f texture1, Vector2f texture2) {
		GL11.glBegin(GL11.GL_QUADS);
		// top
		GL11.glTexCoord2f(0.5f * (texture1.x), 0.5f * (texture1.y));
		GL11.glVertex2f(32, 32);

		GL11.glTexCoord2f(0.5f * (texture1.x + 1), 0.5f * (texture1.y));
		GL11.glVertex2f(64, 48);

		GL11.glTexCoord2f(0.5f * (texture1.x + 1), 0.5f * (texture1.y + 1));
		GL11.glVertex2f(32, 64);

		GL11.glTexCoord2f(0.5f * (texture1.x), 0.5f * (texture1.y + 1));
		GL11.glVertex2f(0, 48);

		// left
		GL11.glTexCoord2f(0.5f * (texture2.x), 0.5f * (texture2.y));
		GL11.glVertex2f(32, 0);

		GL11.glTexCoord2f(0.5f * (texture2.x + 1), 0.5f * (texture2.y));
		GL11.glVertex2f(0, 16);

		GL11.glTexCoord2f(0.5f * (texture2.x + 1), 0.5f * (texture2.y + 1));
		GL11.glVertex2f(0, 48);

		GL11.glTexCoord2f(0.5f * (texture2.x), 0.5f * (texture2.y + 1));
		GL11.glVertex2f(32, 32);

		// right
		GL11.glTexCoord2f(0.5f * (texture2.x), 0.5f * (texture2.y));
		GL11.glVertex2f(32, 0);

		GL11.glTexCoord2f(0.5f * (texture2.x + 1), 0.5f * (texture2.y));
		GL11.glVertex2f(64, 16);

		GL11.glTexCoord2f(0.5f * (texture2.x + 1), 0.5f * (texture2.y + 1));
		GL11.glVertex2f(64, 48);

		GL11.glTexCoord2f(0.5f * (texture2.x), 0.5f * (texture2.y + 1));
		GL11.glVertex2f(32, 32);
		GL11.glEnd();
	}

	@Override
	public void Destroy() {

		super.Destroy();
	}

}
