
import static org.lwjgl.opengl.GL11.glCallList;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import Engine.GLColor;
import Engine.GLMaterial;
import Engine.GLObject;
import Engine.GLProperty;
import Engine.GLRenderer;
import Engine.GLScript;
import Engine.GLShader;
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
				GLShader shader = new GLShader("screen.vert", "screen.frag");
				GLTransform transform = new GLTransform(x * 32, y * 32);
				GLRenderer spriteRenderer = new GLRenderer();
				GLObject obj2 = new GLObject();
				obj2.setName("Dirt");
				obj2.AddComponent(transform);
				obj2.AddComponent(mat);
				obj2.AddComponent(shader);
				obj2.AddComponent(spriteRenderer);
				objects.add(obj2);
			}
		}

		GLMaterial mat = new GLMaterial();
		//mat.setColor(new GLColor(255, 0, 0));
		mat.setTextureID(new GLTextureLoader().getTextureId("resources/textures/grass.png"));
		GLTransform transform = new GLTransform(0, 0);
		GLScript script = new GLScript("resources/scripts/main.lua");

		GLShader shader = new GLShader("screen.vert", "screen.frag");
		GLRenderer spriteRenderer = new GLRenderer();
		GLProperty health = new GLProperty();
		health.setName("health");
		health.setIntValue(100);
		
		GLObject obj = new GLObject();
		obj.setName("Grass");
		obj.AddComponent(transform);
		obj.AddComponent(script);
		obj.AddComponent(mat);
		obj.AddComponent(shader);
		obj.AddComponent(spriteRenderer);
		obj.AddProperty(health);
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
			GLRenderer spriteRenderer = (GLRenderer) obj.getComponent("spriterenderer");
			if (script != null) {
				script.Run();
			}if (shader != null) {
				shader.Run();

			}

			if (transform != null) {
				GL11.glPushMatrix();
				GL11.glTranslatef(transform.getPosition().getX() + transform.getCenter().getX(),transform.getPosition().getY() + transform.getCenter().getY(), 0);
				GL11.glRotatef(transform.getRotation(), 0, 0, 1);
				GL11.glTranslatef(-transform.getCenter().getX(), -transform.getCenter().getY(), 0);
			}



			if (mat != null) {
				GLColor color = mat.getColorAsFloats();
				float[] colorData = { color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() };
				shader.sendUniform4f("vertColor", colorData);
				
				shader.sendTexture("myTexture", mat.getTextureID());
			}
			
			spriteRenderer.Run();
			
			if (transform != null) {
				GL11.glPopMatrix();
			}

			GL11.glColor3f(1, 1, 1);
		}
	}
}
