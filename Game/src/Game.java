import static org.lwjgl.opengl.GL11.glCallList;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game extends GLWindow {

	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	Texture texture;
	GLLoader loader = new GLLoader();
	ShaderProgram shader = new ShaderProgram();
	
	
	@Override
	public void init() {
		super.init();
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/textures/main.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		quad = loader.getQuad("res/quads/square.quad");
		
		shader.createProgram();
		
	}

	GLQuad quad = new GLQuad();

	@Override
	public void update() {
		super.update();
		
	}

	@Override
	public void render() {
		super.render();
		shader.Start();
		shader.sendUniform1f("myTexture", texture.getTextureID());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		//GL11.glColor3f(1, 1, 1);
		glCallList(quad.displayList);
		GL11.glEnd();
	}

	@Override
	public void destroy() {
		shader.Destroy();
		super.destroy();
	}
}