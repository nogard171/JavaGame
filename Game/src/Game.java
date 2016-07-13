import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class Game extends Window {
	
	public void Init() {
		super.Init();
	}
	boolean moved = false;
	public void Update(int delta) {
		super.Update(delta);
		
		
		super.keyboard.endPoll();
	}
	Sprite test = new Sprite(32,32);
	public void Render() {
		super.Render();
		test.Render(0,0);
	}

	public static void main(String[] argv) {
		Game displayExample = new Game();
		displayExample.start();

	}
}