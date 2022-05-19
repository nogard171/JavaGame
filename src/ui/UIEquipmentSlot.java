package ui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Action;
import classes.Size;
import threads.UIThread;
import utils.Renderer;

public class UIEquipmentSlot extends UIItemSlot {

	UIPanel panel;
	String icon = "";
	ArrayList<String> allowItems = new ArrayList<String>();

	public void setIcon(String newIcon) {
		this.icon = newIcon;
	}

	public UIEquipmentSlot() {
		panel = new UIPanel();
		panel.title = "EQUIPMENT_";
		panel.tileSize = new Vector2f(3, 3);
	}

	@Override
	public void setPosition(Vector2f newPosition) {
		super.setPosition(newPosition);
		panel.setPosition(this.getPosition());
	}

	@Override
	public void update() {
		super.update();
		if (panel != null) {
			panel.update();
		}
	}

	@Override
	public void render() {
		super.render();
		if (panel != null) {
			panel.render();
		}
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		Renderer.renderModel("ITEM", icon, this.getPosition().x - 1, this.getPosition().y - 1);
		Renderer.renderModel("ITEM", this.item, this.getPosition().x, this.getPosition().y);
		GL11.glEnd();
	}
}
