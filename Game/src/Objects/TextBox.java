package Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


import events.Action;
import util.*;

public class TextBox {
	private String Text = "";
	private String passwordText = "";
	public boolean Focus = false;
	private Font font = new Font ("Arial", this.style , 12);
	private int style = Font.PLAIN;
	private int X = 0;
	private int Y = 0;
	private int time = 0;
	private int width = 100;
	private int height = 20;
	boolean isPassword = false;
	private String passwordChar = "*";
	boolean isHovered = false;

	public String getText() {
		if (this.isPassword) {
			return this.passwordText;
		} else {
			return this.Text;
		}
	}

	public TextBox(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public TextBox(int x, int y, boolean isPasswordTemp) {
		this.X = x;
		this.Y = y;
		this.isPassword = isPasswordTemp;
	}

	public void Draw(Graphics g) {
		g.setFont(font);
		g.setColor(Color.white);
		g.fillRect(this.X, this.Y, width, height);

		if (!this.isHovered) {
			g.setColor(Color.black);
		} else {
			g.setColor(Color.lightGray);
		}
		g.drawRect(this.X, this.Y, width, height);
		g.setColor(Color.black);
		
		g.drawString(Text, this.X + 2, this.Y + 15);
		if (Focus) {
			time++;
			if (time > 500 && time < 1000) {

				g.setColor(Color.white);
			} else if (time >= 1000) {
				g.setColor(Color.black);
				time = 0;
			}
			FontMetrics metrics = g.getFontMetrics(g.getFont());
			// get the advance of my text in this font
			// and render context
			int adv = metrics.stringWidth(Text);
			// calculate the size of a box to hold the
			// text with some padding.
			size = new Dimension(adv + 2, 0);
			g.fillRect(this.X + 2 + size.width, this.Y + 2, 2, 17);
			g.setColor(Color.black);
		}
	}

	Dimension size = new Dimension(0, 0);

	public void addCharacter(String character) {
		if (size.width + 8 < width) {
			if (this.isPassword) {
				this.passwordText += character;
				this.Text += passwordChar;
			} else {
				this.Text += character;
			}

		}
	}

	public void backSpace() {
		if (Text.length() > 0) {
			if (this.isPassword) {
				this.passwordText = this.passwordText.substring(0, this.passwordText.length() - 1);
				this.Text = this.Text.substring(0, this.Text.length() - 1);
			} else {
				this.Text = this.Text.substring(0, this.Text.length() - 1);
			}
		}
	}

	public void input(InputHandler input) {
		if (Focus) {
			for (int k = 48; k < 90; k++) {
				if (input.keyHit == k && !input.isKeyDown(KeyEvent.VK_SHIFT)) {
					char b = (char) k;
					addCharacter((b + "").toLowerCase());
					input.keyHit = -1;
				}
				if (input.keyHit == k && input.isKeyDown(KeyEvent.VK_SHIFT)) {
					char b = (char) k;
					addCharacter(b + "");
					input.keyHit = -1;
				}
			}
			if (input.keyHit == KeyEvent.VK_SPACE) {
				addCharacter(" ");
				input.keyHit = -1;
			}

			if (input.keyHit == KeyEvent.VK_BACK_SPACE) {
				backSpace();
				input.keyHit = -1;
			}
		}
	}

	public void onHover(Action act) {
		if (this.isHovered) {
			act.actionPerformed();
		}
	}

	public void click(InputHandler mouse) {
		if (mouse.buttonDownOnce(1)) {
			// TODO Auto-generated method stub
			if (new Rectangle(this.X, this.Y, this.width, this.height).contains(mouse.getPosition())) {
				this.Focus = true;
			} else {
				this.Focus = false;
			}
		}
		if (new Rectangle(this.X, this.Y, this.width, this.height).contains(mouse.getPosition())) {
			this.isHovered = true;
		} else {
			this.isHovered = false;
		}
	}
}
