import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

public class Entity extends Sprite {
	public int x = 100;
	public int y = 100;
	public int rotX = 0;
	public int rotY = 0;
	protected boolean isHovered;
	protected boolean Focus = false;

	public Entity(int i, int j) {
		this.x = i;
		this.y = j;
		this.width = 100;
		this.height = 30;
	}

	public Entity() {

	}

	public void onClick(MouseInput mouse, Action act) {
		this.onHover(mouse);
		if (mouse.mouseDown(0)) {
			// TODO Auto-generated method stub
			if (this.isHovered) {
				this.Focus = true;

			} else {
				this.Focus = false;
			}
		}
	}

	public void onKey(KeyboardInput keyboard, Action act) {
		 act.actionPerformed();
	}

	public void onHover(MouseInput mouse) {

		if (new Rectangle(this.x, this.x, this.width, this.height).contains(mouse.getPosition())) {
			this.isHovered = true;
		} else {
			this.isHovered = false;
		}
	}

	public void Update(float delta) {
	}

	int time = 0;

	public void Render() {
		if (texture != null) {
			texture.bind();
		} else {
			GL11.glColor3f(1, 0, 0);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(rotX, 0f, 0f, 1f);
		GL11.glTranslatef(-x, -y, 0);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public void Move(float xSpeed, float ySpeed) {
		this.x += xSpeed;
		this.y += ySpeed;
	}

}