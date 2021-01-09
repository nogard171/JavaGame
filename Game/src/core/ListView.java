package core;

import java.awt.Rectangle;
import java.util.LinkedList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.MenuItem;
import utils.Renderer;

public class ListView {
	public Vector2f position = new Vector2f(0, 0);
	private LinkedList<MenuItem> items = new LinkedList<MenuItem>();

	public void init() {

	}

	public void addItem(MenuItem item) {
		items.add(item);
	}

	public void update() {
		position = new Vector2f((Window.getWidth() / 2) - 50, (Window.getHeight() / 2) - (16 * items.size()));
		int y = 0;
		for (MenuItem item : items) {
			if (item.bounds == null) {
				item.bounds = new Rectangle((int) position.x, (int) position.y, 150, 32);
			} else {
				item.bounds.x = (int) position.x;
				item.bounds.y = (int) position.y + ((item.bounds.height + 2) * y);
			}
			item.update();
			y++;
		}
	}

	public void render() {

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int y = 0;

		for (MenuItem item : items) {
			Color c = new Color(1, 1, 1, 0.5f);
			if (item.hovered) {
				c = new Color(1, 0, 0, 0.5f);
			}
			Renderer.beginDraw(GL11.GL_QUADS);
			Renderer.drawQuad(item.bounds.x, item.bounds.y, item.bounds.width, item.bounds.height + 2, c);
			Renderer.endDraw();
			Renderer.drawText(item.bounds.x, item.bounds.y, item.getName(), item.bounds.height, Color.black);
			y++;
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void destroy() {
		items = null;
	}

}
