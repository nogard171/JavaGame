package threads;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Position;
import core.GameData;
import core.Input;
import core.MainMenu;
import core.PlayerController;
import core.ViewController;
import core.Window;
import core.World;
import utils.FPS;
import utils.Loader;
import utils.Renderer;

public class GameThread extends BaseThread {
	World world;
	MainMenu menu;

	@Override
	public void init() {
		super.init();
		Loader.loadTextures();
		menu = new MainMenu();
		menu.init();
		world = new World();
		world.init();
	}

	@Override
	public void update() {
		super.update();
		menu.update();
		world.update();

	}

	@Override
	public void render() {
		super.render();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GameData.texture.getTextureID());
		world.render();
		menu.render();
	}

	@Override
	public void destroy() {
		world.destroy();
		menu.destroy();
		super.destroy();
	}
}
