
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import Engine.GLColor;
import Engine.GLMaterial;
import Engine.GLObject;
import Engine.GLScript;
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

				GLTransform transform = new GLTransform(x * 32, y * 32);
				GLObject obj2 = new GLObject();
				obj2.AddComponent(transform);
				objects.add(obj2);

			}
		}
		GLMaterial mat = new GLMaterial();
		mat.setColor(new GLColor(255,0,0));
		GLTransform transform = new GLTransform(100, 100);
		GLScript script = new GLScript("resources/main.lua");
		GLObject obj = new GLObject();
		obj.AddComponent(transform);
		obj.AddComponent(script);
		obj.AddComponent(mat);
		objects.add(obj);
	}

	// GLObject obj;

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
			if (script != null) {
				script.Run();
			}
			if (transform != null) {
				GL11.glPushMatrix();
				GL11.glTranslatef(transform.getPosition().getX(), transform.getPosition().getY(), 0);
				if (mat != null) {
					GLColor color = mat.getColorAsFloats();
					GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
				}
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glVertex2f(0, 0);
				GL11.glVertex2f(32, 0);
				GL11.glVertex2f(32, 32);
				GL11.glVertex2f(0, 32);
				GL11.glEnd();
				GL11.glPopMatrix();
				GL11.glColor3f(1,1,1);
			}

		}
	}
}
