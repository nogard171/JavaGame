package core;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import classes.*;
import utils.Renderer;

public class MainMenu {
	ListView menu;

	public void init() {
		menu = new ListView();

		MenuItem resume = new MenuItem(new AFunction() {
			public void click(MenuItem item) {
				System.out.println(item.getName());
			}
		});
		resume.setName("Resume");
		menu.addItem(resume);

		MenuItem settings = new MenuItem(new AFunction() {
			public void click(MenuItem item) {
				System.out.println(item.getName());
			}
		});
		settings.setName("Settings");
		menu.addItem(settings);

		MenuItem help = new MenuItem(new AFunction() {
			public void click(MenuItem item) {
				System.out.println(item.getName());
			}
		});
		help.setName("Help");
		menu.addItem(help);

		MenuItem exit = new MenuItem(new AFunction() {
			public void click(MenuItem item) {
				System.out.println(item.getName());
			}
		});
		exit.setName("Exit");
		menu.addItem(exit);

	}

	public void update() {
		menu.update();
	}

	public void render() {
		Renderer.beginDraw(GL11.GL_QUADS);
		Renderer.drawQuad(0, 0, Window.getWidth(), Window.getHeight(), new Color(0, 0, 0, 0.75f));
		Renderer.endDraw();
		menu.render();

	}

	public void destroy() {

	}
}
