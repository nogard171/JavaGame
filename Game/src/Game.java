import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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
		GLTransform transform = new GLTransform(100, 100);
		GLScript script = new GLScript("resources/main.lua");
		obj = new GLObject();
		obj.AddComponent(transform);
		obj.AddComponent(script);
	}

	GLObject obj;

	@Override
	public void Update() {
		super.Update();

		
	}

	@Override
	public void Render() {
		super.Render();
		
		GLTransform transform = (GLTransform) obj.getComponent("transform");
		GLScript script = (GLScript) obj.getComponent("script");
		script.Run();
		GL11.glPushMatrix();
		GL11.glTranslatef(transform.getPosition().getX(),transform.getPosition().getY(),0);
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex2f(0,0);
		GL11.glVertex2f(32,0);
		GL11.glVertex2f(32,32);
		GL11.glVertex2f(0,32);
		
		GL11.glEnd();
		
		GL11.glPopMatrix();
		
	}
}
