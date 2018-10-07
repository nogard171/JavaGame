
import static org.lwjgl.opengl.GL11.glCallList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import components.GLComponent;
import components.GLTransform;
import engine.GLDisplay;

public class Game {
	private GLDisplay display;
	
	public void Start() {
		this.display = new GLDisplay(800,600);
		this.display.Create();
		
		GLTransform test = new GLTransform();
		
		System.out.println("Type: " +test.GetType());
		
		Setup();
		GameLoop();
	}

	public void Setup() {

	}
	
	public void GameLoop()
	{
		while(!display.IsClosed())
		{
			this.Update();
			this.Render();
		}
	}

	public void Update() {
		display.Update();
	}

	public void Destroy() {
	}

	public void Render() {
	}
}
