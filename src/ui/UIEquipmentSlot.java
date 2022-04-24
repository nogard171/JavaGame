package ui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import utils.Renderer;

public class UIEquipmentSlot extends UIPanel {
	public String icon = "";

	public UIEquipmentSlot() {

	}

	public UIEquipmentSlot(String newIcon) {
		this.icon = newIcon;
	}

	@Override
	protected void build() {
		int width = Math.round((float) this.getSize().getWidth() / (float) 3);
		int height = Math.round((float) this.getSize().getHeight() / (float) 3);
		displayListID = GL11.glGenLists(1);
		GL11.glNewList(displayListID, GL11.GL_COMPILE_AND_EXECUTE);
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Vector2f position = new Vector2f((x * 3) + this.getPosition().x, (y * 3) + this.getPosition().y);
				if (x == 0 && y == 0) {
					Renderer.renderModel("EQUIPMENT_PANEL_TILE", "EQUIPMENT_PANEL_TL", position.x, position.y);
				} else if (x == 0 && y < height - 1) {
					Renderer.renderModel("EQUIPMENT_PANEL_TILE", "EQUIPMENT_PANEL_CL", position.x, position.y);
				} else if (x == 0 && y == height - 1) {
					Renderer.renderModel("EQUIPMENT_PANEL_TILE", "EQUIPMENT_PANEL_BL", position.x, position.y);
				} else if (x == width - 1 && y == 0) {
					Renderer.renderModel("EQUIPMENT_PANEL_TILE", "EQUIPMENT_PANEL_TR", position.x, position.y);
				} else if (x == width - 1 && y < height - 1) {
					Renderer.renderModel("EQUIPMENT_PANEL_TILE", "EQUIPMENT_PANEL_CR", position.x, position.y);
				} else if (x == width - 1 && y == height - 1) {
					Renderer.renderModel("EQUIPMENT_PANEL_TILE", "EQUIPMENT_PANEL_BR", position.x, position.y);
				} else if (y == 0) {
					Renderer.renderModel("EQUIPMENT_PANEL_TILE", "EQUIPMENT_PANEL_TM", position.x, position.y);
				} else if (y < height - 1) {
					Renderer.renderModel("EQUIPMENT_PANEL_TILE", "EQUIPMENT_PANEL_CM", position.x, position.y);
				} else if (y == height - 1) {
					Renderer.renderModel("EQUIPMENT_PANEL_TILE", "EQUIPMENT_PANEL_BM", position.x, position.y);
				}
			}
		}
		Renderer.renderModel("EQUIPMENT_ICON", icon, this.getPosition().x+5, this.getPosition().y+5);

		GL11.glEnd();
		GL11.glEndList();
	}
}
