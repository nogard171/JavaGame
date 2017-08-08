import static org.lwjgl.opengl.GL11.glCallList;

import org.lwjgl.opengl.GL11;

public class Game extends GLWindow {

	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}

	GLLoader loader = new GLLoader();

	@Override
	public void init() {
		super.init();
		quad = loader.getQuad("res/quads/square.quad");
	}

	GLQuad quad = new GLQuad();

	@Override
	public void update() {
		super.update();

	}

	@Override
	public void render() {
		super.render();
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(1, 1, 1);
		glCallList(quad.displayList);
		GL11.glEnd();
	}

	@Override
	public void destroy() {
		super.destroy();

	}
}