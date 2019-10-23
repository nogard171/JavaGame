package threads;

import java.awt.Color;
import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import classes.Action;
import classes.UIButton;
import classes.UIControl;
import classes.UIMenu;
import data.MaterialData;
import data.UIData;
import utils.Loader;
import utils.Renderer;

public class UIThread extends BaseThread {

	@Override
	public void setup() {
		super.setup();

		Loader.loadFonts();

		UIMenu menu = new UIMenu("test");

		UIButton test = new UIButton();
		test.setName("move");
		test.setText("Move");
		test.setPosition(new Vector2f(5, 5));
		test.onClick(new Action() {
			public void run(UIControl self) {

			}
		});

		menu.addButton(test);
		UIData.controls.put(menu.getName(), menu);

	}

	@Override
	public void update() {
		super.update();
		for (UIControl control : UIData.controls.values()) {
			control.update();
		}
	}

	@Override
	public void render() {
		super.render();

		for (UIControl control : UIData.controls.values()) {
			Renderer.renderControl(control);
		}
		Renderer.renderText("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 100, 100, 12, Color.WHITE);
	}

	@Override
	public void clean() {

		super.clean();
	}

	public static void showMenu(String string) {

	}
}
