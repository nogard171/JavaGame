import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
	public Point screenPosition = new Point(0, 0);
	public int rotX = 0;
	public int rotY = 0;
	public Direction isFacing = Direction.NORTH;
	protected boolean isHovered;
	protected boolean Focus = false;
	public boolean isSolid = false;
	public boolean movable = false;

	public Entity(int i, int j) {
		this.x = i;
		this.y = j;
		screenPosition = new Point(i, j);
		this.width = 32;
		this.height = 32;
	}

	public Entity() {

	}

	public Entity(int i, int j, int k, int l) {
		// TODO Auto-generated constructor stub
		screenPosition = new Point(i, j);
		this.x = i;
		this.y = j;
		this.width = k;
		this.height = l;
	}

	int clicked = 0;

	public void onClick(MouseInput mouse, Action act) {
		this.onHover(mouse);
		if (mouse.mouseDown(0)) {
			// TODO Auto-generated method stub
			if (this.isHovered) {
				this.Focus = true;

				if (clicked == 0) {
					clicked++;
					act.actionPerformed();
				} else {
					clicked = 0;
				}

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
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1, 1, 1);
			texture.bind();
			
		} else {
			GL11.glColor3f(1, 0, 0);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(screenPosition.x, screenPosition.y, 0);
		GL11.glRotatef(rotX, 0f, 0f, 1f);
		
		GL11.glBegin(GL11.GL_QUADS);
		if (texture != null) {
			
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(texture.getImageWidth(), 0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(texture.getImageWidth(), texture.getImageHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, texture.getImageHeight());
		} else {
			GL11.glVertex2f(0, 0);
			GL11.glVertex2f(width, 0);
			GL11.glVertex2f(width, height);
			GL11.glVertex2f(0, height);
		}
		GL11.glEnd();
		
		if (topTexture != null) {
			GL11.glColor3f(1, 1, 1);
			topTexture.bind();
			
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(topTexture.getImageWidth(), 0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(topTexture.getImageWidth(), topTexture.getImageHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, topTexture.getImageHeight());
			GL11.glEnd();
		}
		
		if (this.movable) {
			GL11.glColor3f(0, 0, 1);
			if (this.isFacing == Direction.EAST) {
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(0, 0);
				GL11.glVertex2f(width / 4, 0);
				GL11.glVertex2f(width / 4, height);
				GL11.glVertex2f(0, height);
				GL11.glEnd();
			} else if (this.isFacing == Direction.WEST) {
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f((width / 4) * 3, 0);
				GL11.glVertex2f(width, 0);
				GL11.glVertex2f(width, height);
				GL11.glVertex2f((width / 4) * 3, height);
				GL11.glEnd();
			} else if (this.isFacing == Direction.NORTH) {
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(0, 0);
				GL11.glVertex2f(width, 0);
				GL11.glVertex2f(width, height / 4);
				GL11.glVertex2f(0, height / 4);
				GL11.glEnd();
			} else if (this.isFacing == Direction.SOUTH) {
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(0, (height / 4) * 3);
				GL11.glVertex2f(width, (height / 4) * 3);
				GL11.glVertex2f(width, height);
				GL11.glVertex2f(0, height);
				GL11.glEnd();
			}
		}
			if (isSolid) {
				GL11.glColor3f(1, 0, 0);
				GL11.glBegin(GL11.GL_LINE_LOOP);
				GL11.glVertex2f(0, 0);
				GL11.glVertex2f(width, 0);
				GL11.glVertex2f(width, height);
				GL11.glVertex2f(0, height);
				GL11.glEnd();
			}
		
		GL11.glTranslatef(-screenPosition.x, -screenPosition.y, 0);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public void Move(float xSpeed, float ySpeed) {

		if ((xSpeed < 0 && collosionDir == Direction.EAST) || (xSpeed > 0 && collosionDir == Direction.WEST)) {
			xSpeed = 0;
		}
		if ((ySpeed < 0 && collosionDir == Direction.NORTH) || (ySpeed > 0 && collosionDir == Direction.SOUTH)) {
			ySpeed = 0;
		}

		this.x += xSpeed;
		this.y += ySpeed;
		if (movable) {
			this.screenPosition.x = this.x;
			this.screenPosition.y = this.y;
		}
		if (xSpeed < 0) {
			this.isFacing = Direction.EAST;
		} else if (xSpeed > 0) {
			this.isFacing = Direction.WEST;
		}
		if (ySpeed < 0) {
			this.isFacing = Direction.NORTH;
		} else if (ySpeed > 0) {
			this.isFacing = Direction.SOUTH;
		}
	}

	public Point getPosition() {
		// TODO Auto-generated method stub
		return new Point(this.x, this.y);
	}

	Point test = new Point(0, 0);
	Direction collosionDir = null;

	public void collide(Entity obj) {
		if (obj.isSolid && new Rectangle(this.x, this.y, this.width, this.height)
				.intersects(new Rectangle(obj.x, obj.y, obj.width, obj.height))) {
			if (collosionDir == null) {
				collosionDir = this.isFacing;
			}
			if (this.isFacing == Direction.EAST) {
				test.x = -1;
			} else if (this.isFacing == Direction.WEST) {
				test.x = 1;
			}
			if (this.isFacing == Direction.NORTH) {
				test.y = -1;
			} else if (this.isFacing == Direction.SOUTH) {
				test.y = 1;
			}
		} else {
			test = new Point(0, 0);
			collosionDir = null;
		}
	}

}