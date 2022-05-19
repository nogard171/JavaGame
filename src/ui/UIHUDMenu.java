package ui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Size;
import utils.Renderer;

public class UIHUDMenu extends UIMenu {

	public UIHUDMenu(String string) {
		super(string);
	}

	@Override
	public void add(UIMenuItem ctl) {
		this.setSize(new Size(((controls.size() + 1) * 32), 32));
		ctl.setPosition(new Vector2f(this.getPosition().x + ((controls.size()) * 32), this.getPosition().y));
		this.controls.add(ctl);
	}

	@Override
	public void render() {
		if (this.isVisible) {
			float x = 0;
			float y = 0;
			Renderer.renderQuad(this.getPosition().getX(), this.getPosition().getY(), this.getSize().getWidth(),
					this.getSize().getHeight(), new Color(0, 0, 0, 0.5f));

			for (UIControl temp : this.getControls()) {
				UIMenuItem item = (UIMenuItem) temp;
				if (item != null) {
					Color bgColor = item.getBackgroundColor();
					if (bgColor != null) {
						Renderer.renderQuad(this.getPosition().x + x, this.getPosition().y + y,
								item.getSize().getWidth(), item.getSize().getHeight(), bgColor);
					}
					if (item.getIcon() != "") {
						GL11.glBegin(GL11.GL_TRIANGLES);
						Renderer.renderModel("ITEM", item.getIcon(), this.getPosition().x + x,
								this.getPosition().y + y);
						GL11.glEnd();
					}
					x += 32;
				}
			}
		}
	}

}