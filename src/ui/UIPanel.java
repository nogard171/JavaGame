package ui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import classes.Size;
import utils.Renderer;

public class UIPanel extends UIControl {
	public String title = "";
	public Vector2f position = new Vector2f(0, 0);
	public Vector2f tileSize = new Vector2f(5, 5);
	public int displayListID = -1;
	public boolean needUpdating = false;

	protected void build() {
		int width = (int) (this.getSize().getWidth() / tileSize.getX());
		int height = (int) (this.getSize().getHeight() / tileSize.getY());
		displayListID = GL11.glGenLists(1);
		GL11.glNewList(displayListID, GL11.GL_COMPILE_AND_EXECUTE);
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Vector2f position = new Vector2f((x * tileSize.getX()) + this.getPosition().x,
						(y * tileSize.getY()) + this.getPosition().y);
				if (x == 0 && y == 0) {
					Renderer.renderModel(title + "PANEL_TILE", title + "PANEL_TL", position.x, position.y);
				} else if (x == 0 && y < height - 1) {
					Renderer.renderModel(title + "PANEL_TILE", title + "PANEL_CL", position.x, position.y);
				} else if (x == 0 && y == height - 1) {
					Renderer.renderModel(title + "PANEL_TILE", title + "PANEL_BL", position.x, position.y);
				} else if (x == width - 1 && y == 0) {
					Renderer.renderModel(title + "PANEL_TILE", title + "PANEL_TR", position.x, position.y);
				} else if (x == width - 1 && y < height - 1) {
					Renderer.renderModel(title + "PANEL_TILE", title + "PANEL_CR", position.x, position.y);
				} else if (x == width - 1 && y == height - 1) {
					Renderer.renderModel(title + "PANEL_TILE", title + "PANEL_BR", position.x, position.y);
				} else if (y == 0) {
					Renderer.renderModel(title + "PANEL_TILE", title + "PANEL_TM", position.x, position.y);
				} else if (y < height - 1) {
					Renderer.renderModel(title + "PANEL_TILE", title + "PANEL_CM", position.x, position.y);
				} else if (y == height - 1) {
					Renderer.renderModel(title + "PANEL_TILE", title + "PANEL_BM", position.x, position.y);
				}
			}
		}
		GL11.glEnd();
		GL11.glEndList();
	}

	@Override
	public void setPosition(Vector2f newPosition) {
		super.setPosition(newPosition);
		this.needUpdating = true;
	}

	@Override
	public void update() {
		super.update();
	}

	public void render() {
		if (this.displayListID == -1 || needUpdating) {
			build();
			this.needUpdating = false;
		}

		if (this.displayListID != -1) {
			GL11.glCallList(this.displayListID);
		}
	}
}
