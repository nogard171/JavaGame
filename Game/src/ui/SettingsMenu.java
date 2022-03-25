package ui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import classes.Action;
import classes.Size;
import utils.FPS;
import utils.Renderer;

public class SettingsMenu extends UIControl {
	UIButton controlsBtn;
	UIButton graphicsBtn;
	UIButton audioBtn;

	int panelActive = 0;

	public void setup() {
		this.isVisible = false;
		setPosition(new Vector2f(200, 100));
		setSize(new Size(454, 400));

		controlsBtn = new UIButton();
		controlsBtn.setText("Controls", 20, Color.white);
		controlsBtn.setBackgroundColor(new Color(0, 0, 0, 0.9f));
		controlsBtn.setPosition(new Vector2f(this.getPosition().getX(), this.getPosition().getY() - 32));
		controlsBtn.setSize(new Size(150, 32));
		controlsBtn.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				UIButton temp = (UIButton) self;
				if (mouseButton == 0) {
					panelActive = 0;
					if (temp != null) {
						temp.setBackgroundColor(new Color(0.15f, 0.15f, 0.15f, 1f));
						temp = (UIButton) audioBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
						temp = (UIButton) graphicsBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
					}
				}
			}

			@Override
			public void onMouseHover(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 0) {
						temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
					}
				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 0) {
						temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
					}
				}
			}
		});
		graphicsBtn = new UIButton();
		graphicsBtn.setText("Graphics", 20, Color.white);
		graphicsBtn.setBackgroundColor(new Color(0, 0, 0, 0.9f));
		graphicsBtn.setPosition(new Vector2f(this.getPosition().getX() + 152, this.getPosition().getY() - 32));
		graphicsBtn.setSize(new Size(150, 32));
		graphicsBtn.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				UIButton temp = (UIButton) self;
				if (mouseButton == 0) {
					panelActive = 1;
					if (temp != null) {
						temp.setBackgroundColor(new Color(0.15f, 0.15f, 0.15f, 1f));
						temp = (UIButton) audioBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
						temp = (UIButton) controlsBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
					}
				}
			}

			@Override
			public void onMouseHover(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 1) {
						temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
					}
				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 1) {
						temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
					}
				}
			}
		});
		audioBtn = new UIButton();
		audioBtn.setText("Audio", 20, Color.white);
		audioBtn.setBackgroundColor(new Color(0, 0, 0, 0.9f));
		audioBtn.setPosition(new Vector2f(this.getPosition().getX() + 304, this.getPosition().getY() - 32));
		audioBtn.setSize(new Size(150, 32));
		audioBtn.onEvent(new Action() {
			@Override
			public void onMouseClick(UIControl self, int mouseButton) {
				UIButton temp = (UIButton) self;
				if (mouseButton == 0) {
					panelActive = 2;
					if (temp != null) {
						temp.setBackgroundColor(new Color(0.15f, 0.15f, 0.15f, 1f));
						temp = (UIButton) controlsBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
						temp = (UIButton) graphicsBtn;
						if (temp != null) {
							temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
						}
					}
				}
			}

			@Override
			public void onMouseHover(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 2) {
						temp.setBackgroundColor(new Color(1, 0, 0, 0.5f));
					}
				}
			}

			@Override
			public void onMouseExit(UIControl self) {
				UIButton temp = (UIButton) self;
				if (temp != null) {
					if (panelActive != 2) {
						temp.setBackgroundColor(new Color(0, 0, 0, 0.9f));
					}
				}
			}
		});
	}

	@Override
	public void update() {
		if (this.isVisible) {
			controlsBtn.update();
			graphicsBtn.update();
			audioBtn.update();
		}
	}

	@Override
	public void render() {
		if (this.isVisible) {
			GL11.glColor3f(1, 1, 1);
			Renderer.renderQuad(this.getPosition().getX(), this.getPosition().getY(), this.getSize().getWidth(),
					this.getSize().getHeight(), new Color(0.15f, 0.15f, 0.15f, 1f));

			Renderer.renderButton(controlsBtn);
			Renderer.renderButton(graphicsBtn);
			Renderer.renderButton(audioBtn);

			switch (this.panelActive) {
			case 0:
				/*
				 * path finding max distance to check different panel keyboard shortcuts Fx
				 * buttons, such as debug, logs, reload?
				 * 
				 * 
				 */
				int y = 0;
				Renderer.renderText(this.getPosition().getX(), this.getPosition().getY(), "Controls Panel", 12,
						Color.white);
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "Path Finding",
						20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 120, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				Renderer.renderText(this.getPosition().getX() + 200, this.getPosition().getY() + 14 + y, "Level 0", 16,
						Color.black);
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y,
						"Show Telemetry", 20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 16, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				y += 30;
				break;
			case 1:
				/*
				 * fullscreen toggle resolution dropdown texture quality? vsync custom non vsync
				 * options: target fps menu fps inactive fps
				 * 
				 */
				y = 0;
				Renderer.renderText(this.getPosition().getX(), this.getPosition().getY(), "Graphics Panel", 12,
						Color.white);

				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "Fullscreen",
						20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 16, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "Resolution",
						20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 120, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				Renderer.renderText(this.getPosition().getX() + 200, this.getPosition().getY() + 14 + y, "Auto",
						16, Color.black);
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "Quality", 20,
						Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 120, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				Renderer.renderText(this.getPosition().getX() + 200, this.getPosition().getY() + 14 + y, "Auto", 16,
						Color.black);
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "Vsync", 20,
						Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 16, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "- Target FPS",
						20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 50, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				Renderer.renderText(this.getPosition().getX() + 200, this.getPosition().getY() + 14 + y, "120", 16,
						Color.black);
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "- Menu FPS",
						20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 50, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				Renderer.renderText(this.getPosition().getX() + 200, this.getPosition().getY() + 14 + y, "60", 16,
						Color.black);
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y,
						"- Inactive FPS", 20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 18 + y, 50, 16,
						new Color(0.8f, 0.8f, 0.8f, 1f));
				Renderer.renderText(this.getPosition().getX() + 200, this.getPosition().getY() + 14 + y, "30", 16,
						Color.black);

				break;
			case 2:
				/*
				 * volume slider for master, sound effects, music.
				 * 
				 */
				Renderer.renderText(this.getPosition().getX(), this.getPosition().getY(), "Audio Panel", 12,
						Color.white);
				y = 0;
				Color sliderColor = new Color(0.4f, 0.4f, 0.4f, 1f);
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "Master Volume",
						20, Color.white);
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 21 + y, 200, 10,
						sliderColor);
				float volumeInt = 100;
				float volumeStep = ((float) (200 - 16)) / (float) 100;
				Renderer.renderQuad(this.getPosition().getX() + 200 + (float) (volumeInt * (float) volumeStep),
						this.getPosition().getY() + 18 + y, 16, 16, new Color(0.8f, 0.8f, 0.8f, 1f));
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "Music", 20,
						Color.white);
				volumeInt = 25;
				volumeStep = ((float) (200 - 16)) / (float) 100;
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 21 + y, 200, 10,
						sliderColor);
				Renderer.renderQuad(this.getPosition().getX() + 200 + (float) (volumeInt * (float) volumeStep),
						this.getPosition().getY() + 18 + y, 16, 16, new Color(0.8f, 0.8f, 0.8f, 1f));
				y += 30;
				Renderer.renderText(this.getPosition().getX() + 10, this.getPosition().getY() + 10 + y, "Sound Effects",
						20, Color.white);
				volumeInt = 50;
				volumeStep = ((float) (200 - 16)) / (float) 100;
				Renderer.renderQuad(this.getPosition().getX() + 200, this.getPosition().getY() + 21 + y, 200, 10,
						sliderColor);
				Renderer.renderQuad(this.getPosition().getX() + 200 + (float) (volumeInt * (float) volumeStep),
						this.getPosition().getY() + 18 + y, 16, 16, new Color(0.8f, 0.8f, 0.8f, 1f));
				y += 30;
				break;
			}

		}
	}
}
