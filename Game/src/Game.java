
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import Engine.GLAnimator;
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

	private Audio wavEffect;

	@Override
	public void Setup() {

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				GLMaterial mat = new GLMaterial();
				mat.setTextureID(new GLTextureLoader().getTextureId("resources/textures/dirt.png"));
				GLShader shader = new GLShader("basic.vert", "basic.frag");
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
		GLTransform transform = new GLTransform(0, 0);
		GLScript script = new GLScript("resources/scripts/main.lua");
		GLShader shader = new GLShader("basic.vert", "basic.frag");
		GLRenderer spriteRenderer = new GLRenderer();
		GLAnimator animator = new GLAnimator("resources/textures/guy.png");
		GLAudio audio = new GLAudio("resources/audio/walking.wav");
		audio.setVolume(0.1f);

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
		obj.AddComponent(animator);
		obj.AddComponent(audio);
		
		obj.AddProperty(health);
		objects.add(obj);

	}

	ArrayList<GLObject> objects = new ArrayList<GLObject>();
	@Override
	public void Update() {
		super.Update();

	}
	@Override
	public void Destroy() {
		super.Destroy();
	}
	@Override
	public void Render() {
		super.Render();

		for (GLObject obj : objects) {
			GLMaterial mat = (GLMaterial) obj.getComponent("material");
			GLTransform transform = (GLTransform) obj.getComponent("transform");
			GLScript script = (GLScript) obj.getComponent("script");
			GLShader shader = (GLShader) obj.getComponent("shader");
			GLRenderer spriteRenderer = (GLRenderer) obj.getComponent("renderer");
			GLAnimator animator = (GLAnimator) obj.getComponent("animator");
			if (script != null) {
				script.Run();
			}
			if (shader != null) {
				shader.Run();

			}

			if (transform != null) {
				GL11.glPushMatrix();
				GL11.glTranslatef(transform.getPosition().getX() + transform.getCenter().getX(),
						transform.getPosition().getY() + transform.getCenter().getY(), 0);
				GL11.glRotatef(transform.getRotation(), 0, 0, 1);
				GL11.glTranslatef(-transform.getCenter().getX(), -transform.getCenter().getY(), 0);
			}

			if (mat != null) {
				GLColor color = mat.getColorAsFloats();
				float[] colorData = { color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() };
				shader.sendUniform4f("vertColor", colorData);

				shader.sendTexture("myTexture", mat.getTextureID());
			}
			if (animator != null) {
				animator.Run();
			}
			spriteRenderer.Run();

			if (transform != null) {
				GL11.glPopMatrix();
			}

			GL11.glColor3f(1, 1, 1);
		}
	}
}
