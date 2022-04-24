package ui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import classes.Size;
import utils.Renderer;

public class UIPanel extends UIControl {
	public Vector2f position = new Vector2f(0, 0);
	public int displayListID = -1;
	public boolean needUpdating = false;

	protected void build() {
		int width = this.getSize().getWidth() / 5;
		int height = this.getSize().getHeight() / 5;
		displayListID = GL11.glGenLists(1);
		GL11.glNewList(displayListID, GL11.GL_COMPILE_AND_EXECUTE);
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Vector2f position = new Vector2f((x * 5) + this.getPosition().x, (y * 5) + this.getPosition().y);
				if (x == 0 && y == 0) {
					Renderer.renderModel("PANEL_TILE", "PANEL_TL", position.x, position.y);
				} else if (x == 0 && y < height - 1) {
					Renderer.renderModel("PANEL_TILE", "PANEL_CL", position.x, position.y);
				} else if (x == 0 && y == height - 1) {
					Renderer.renderModel("PANEL_TILE", "PANEL_BL", position.x, position.y);
				} else if (x == width - 1 && y == 0) {
					Renderer.renderModel("PANEL_TILE", "PANEL_TR", position.x, position.y);
				} else if (x == width - 1 && y < height - 1) {
					Renderer.renderModel("PANEL_TILE", "PANEL_CR", position.x, position.y);
				} else if (x == width - 1 && y == height - 1) {
					Renderer.renderModel("PANEL_TILE", "PANEL_BR", position.x, position.y);
				} else if (y == 0) {
					Renderer.renderModel("PANEL_TILE", "PANEL_TM", position.x, position.y);
				} else if (y < height - 1) {
					Renderer.renderModel("PANEL_TILE", "PANEL_CM", position.x, position.y);
				} else if (y == height - 1) {
					Renderer.renderModel("PANEL_TILE", "PANEL_BM", position.x, position.y);
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
