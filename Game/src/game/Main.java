package game;

import java.awt.event.KeyEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import engine.GLCamera;
import engine.GLDisplay;
import engine.GLShader;

public class Main extends GLDisplay {
	KeyHandler keys;
	GLCamera camera;

	@Override
	public void Setup() {
		super.Setup();
		keys = new KeyHandler();
		camera = new GLCamera();
		shader = new GLShader();
		shader.createProgram();
	}

	@Override
	public void Update() {
		super.Update();
		keys.Update();
		camera.poll(1);
		camera.apply();
		camera.acceptInputMove(1);
	}
	GLShader shader;
	@Override
	public void Render() {
		super.Render();
		
		shader.Start();
		
		//shader.sendUniform3f("position", position);
		
		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3f(0, 0,5);
		GL11.glVertex3f(1, 0,5);
		GL11.glVertex3f(0, 1,5);
		GL11.glVertex3f(1, 1,5);
		
		GL11.glEnd();

	}

	@Override
	public void Destroy() {
		
		super.Destroy();

	}
}
