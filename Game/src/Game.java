
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import Engine.GLColor;
import Engine.GLMaterial;
import Engine.GLObject;
import Engine.GLScript;
import Engine.GLShader;
import Engine.GLTransform;
import Engine.GLWindow;

public class Game extends GLWindow {
	public void Start() {
		super.Create();

	}

	@Override
	public void Setup() {

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				GLMaterial mat = new GLMaterial();
				mat.setColor(new GLColor(0, 255, 0));
				GLTransform transform = new GLTransform(x * 32, y * 32);
				GLObject obj2 = new GLObject();
				obj2.AddComponent(transform);
				obj2.AddComponent(mat);
				objects.add(obj2);
			}
		}

		GLMaterial mat = new GLMaterial();
		mat.setColor(new GLColor(255, 0, 0));
		GLTransform transform = new GLTransform(0, 0);
		GLScript script = new GLScript("resources/scripts/main.lua");

		GLShader shader = new GLShader();

		GLObject obj = new GLObject();
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
			// GLShader shader = (GLShader) obj.getComponent("shader");
			if (script != null) {
				script.Run();
			}
			if (transform != null) {
				
				GL11.glPushMatrix();
				GL11.glTranslatef(transform.getPosition().getX()+transform.getCenter().getX(), transform.getPosition().getY()+transform.getCenter().getY(), 0);
				GL11.glRotatef(transform.getRotation(), 0, 0,1);
				GL11.glTranslatef(-transform.getCenter().getX(), -transform.getCenter().getY(), 0);
				
				GLColor color = new GLColor(255, 255, 255);
				if (mat != null) {
					color = mat.getColorAsFloats();
					GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
				}
				/*
				 * if (shader != null) { shader.Run(); float[] colorData = {
				 * color.getRed(), color.getGreen(), color.getBlue(),
				 * color.getAlpha() }; shader.sendUniform4f("vertColor",
				 * colorData); }
				 */

				GL11.glBegin(GL11.GL_QUADS);

				GL11.glVertex2f(0, 0);
				GL11.glVertex2f(32, 0);
				GL11.glVertex2f(32, 32);
				GL11.glVertex2f(0, 32);

				GL11.glEnd();
				GL11.glPopMatrix();
				GL11.glColor3f(1, 1, 1);
			}
		}
	}
}
