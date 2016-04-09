package Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Label {
	private String Text = "";
	private Color color = Color.black;
	private Font font = new Font("Arial", this.style, 12);
	private int style = Font.PLAIN;
	private int X = 0;
	private int Y = 0;
	private int width = 100;
	private int height = 20;

	public String getText() {
		return this.Text;

	}

	public Label(String newText, int x, int y) {
		this.Text = newText;
		this.X = x;
		this.Y = y;
	}

	public Label(String newText, int x, int y, Font newFont) {
		this.Text = newText;
		this.X = x;
		this.Y = y;
		this.font = newFont;
	}

	public void Draw(Graphics g) {
		g.setFont(font);
		g.setColor(color);
		g.drawString(Text, this.X + 2, this.Y + 15);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
	}

	public void setText(String newText) {
		this.Text = newText;
	}

	public void setColor(Color newColor) {
		this.color = newColor;
	}
}
