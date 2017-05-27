
import static org.lwjgl.opengl.GL11.glCallList;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import Engine.GLColor;
import Engine.GLMaterial;
import Engine.GLObject;
import Engine.GLScript;
import Engine.GLShader;
import Engine.GLSpriteRenderer;
import Engine.GLTransform;
import Engine.GLWindow;
import Utils.GLTextureLoader;

public class Game extends GLWindow {
	public void Start() {
		super.Create();

	}

	@Override
	public void Setup() {

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				GLMaterial mat = new GLMaterial();

				mat.setTextureID(new GLTextureLoader().getTextureId("resources/textures/dirt.png"));
				mat.setColor(new GLColor(255, 255, 255));
				GLShader shader = new GLShader("screen.vert", "screen.frag");
				GLTransform transform = new GLTransform(x * 32, y * 32);
				GLObject obj2 = new GLObject();
				obj2.setName("Dirt");
				obj2.AddComponent(transform);
				obj2.AddComponent(mat);
				obj2.AddComponent(shader);
				objects.add(obj2);
			}
		}

		GLMaterial mat = new GLMaterial();
		mat.setColor(new GLColor(255, 0, 0));
		mat.setTextureID(new GLTextureLoader().getTextureId("resources/textures/grass.png"));
		GLTransform transform = new GLTransform(0, 0);
		GLScript script = new GLScript("resources/scripts/main.lua");

		GLShader shader = new GLShader("screen.vert", "screen.frag");

		GLObject obj = new GLObject();
		obj.setName("Grass");
		obj.AddComponent(transform);
		obj.AddComponent(script);
		obj.AddComponent(mat);
		obj.AddComponent(shader);
		objects.add(obj);
	}

	ArrayList<GLObject> objects = new ArrayList<GLObject>();

	@Override
	public void Update() {
		super.Update();
	}

	@Override
	public void Render() {
		super.Render();

		for (GLObject obj : objects) {
			GLMaterial mat = (GLMaterial) obj.getComponent("material");
			GLTransform transform = (GLTransform) obj.getComponent("transform");
			GLScript script = (GLScript) obj.getComponent("script");
			GLShader shader = (GLShader) obj.getComponent("shader");
			if (script != null) {
				script.Run();
			}

			if (shader != null) {
				shader.Run();
				if (mat != null) {
					GLColor color = mat.getColorAsFloats();
					float[] colorData = { color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() };
					shader.sendTexture("myTexture", mat.getTextureID());
					shader.sendUniform4f("vertColor", colorData);
				}

			}

			if (transform != null) {
				GL11.glPushMatrix();
				GL11.glTranslatef(transform.getPosition().getX() + transform.getCenter().getX(),
						transform.getPosition().getY() + transform.getCenter().getY(), 0);
				GL11.glRotatef(transform.getRotation(), 0, 0, 1);
				GL11.glTranslatef(-transform.getCenter().getX(), -transform.getCenter().getY(), 0);
			}
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, 32);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(32, 32);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(32, 0);
			GL11.glEnd();
			if (transform != null) {
				GL11.glPopMatrix();
			}

			GL11.glColor3f(1, 1, 1);
		}
	}
}
