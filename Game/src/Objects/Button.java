package Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.Callable;

import events.Action;
import util.*;

public class Button {
	String Text = "";
	private int X = 0;
	private int Y = 0;
	private int time = 0;
	private int width = 100;
	private int height = 20;
	boolean isDown = false;
	boolean isDownOnce = false;
	boolean isHovered = false;

	public Button(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public Button(String newText, int x, int y) {
		this.Text = newText;
		this.X = x;
		this.Y = y;
	}

	public void Draw(Graphics g) {

		if (isDown) {
			g.setColor(new Color(192, 192, 192));
		} else if (isHovered) {
			g.setColor(new Color(240, 240, 240));
		} else {
			g.setColor(new Color(224, 224, 224));
		}
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		// get the advance of my text in this font
		// and render context
		int adv = metrics.stringWidth(Text);
		g.fillRect(this.X, this.Y, adv + 5, height);
		g.setColor(Color.black);
		g.drawRect(this.X, this.Y, adv + 5, height);

		g.drawString(this.Text, this.X + 2, this.Y + 15);
	}

	public void onClick(Action act) {
		if (this.isDownOnce) {
			act.actionPerformed();
		}
	}

	public void onHover(Action act) {
		if (this.isHovered) {
			act.actionPerformed();
		}
	}

	public void click(InputHandler mouse) {
		// TODO Auto-generated method stub
		if (mouse.buttonDown(1)
				&& new Rectangle(this.X, this.Y, this.width, this.height).contains(mouse.getPosition())) {
			this.isDown = true;
		} else {
			this.isDown = false;
		}
		if (new Rectangle(this.X, this.Y, this.width, this.height).contains(mouse.getPosition())) {
			this.isHovered = true;
		} else {
			this.isHovered = false;
		}
		if (mouse.buttonDownOnce(1)
				&& new Rectangle(this.X, this.Y, this.width, this.height).contains(mouse.getPosition())) {
			this.isDownOnce = true;
		} else {
			this.isDownOnce = false;
		}
	}
}
