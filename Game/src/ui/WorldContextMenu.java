package ui;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Action;
import classes.Size;
import data.EngineData;

public class WorldContextMenu extends UIControl {
	public void setup() {
		UIMenu menu = new UIMenu("test");
		menu.setPosition(new Vector2f(100, 100));
		menu.setSize(new Size(0, 0));
		menu.onEvent(new Action() {
			@Override
			public void onMouseExit(UIControl self) {
				self.toggle();
				EngineData.blockInput = false;
			}
		});

		menu.toggle();
		EngineData.controls.put(menu.getName(), menu);
	}
}
