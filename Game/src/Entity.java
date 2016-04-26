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
	public int rotX = 0;
	public int rotY = 0;
	public Direction isFacing = Direction.NORTH;
	protected boolean isHovered;
	protected boolean Focus = false;

	public Entity(int i, int j) {
		this.x = i;
		this.y = j;
		this.width = 32;
		this.height = 32;
	}

	public Entity() {

	}

	public Entity(int i, int j, int k, int l) {
		// TODO Auto-generated constructor stub
		this.x = i;
		this.y = j;
		this.width = k;
		this.height = l;
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
		if((xSpeed<0&&collosionDir==Direction.EAST)||(xSpeed>0&&collosionDir == Direction.WEST))
		{
			xSpeed=0;
		}
		if((ySpeed<0&&collosionDir ==Direction.NORTH)||(ySpeed>0&&collosionDir == Direction.SOUTH))
		{
			ySpeed=0;
		}
		this.x += xSpeed;
		this.y += ySpeed;
		if(xSpeed<0)
		{
			this.isFacing = Direction.EAST;
		}
		else if(xSpeed>0)
		{
			this.isFacing = Direction.WEST;
		}		
		if(ySpeed<0)
		{
			this.isFacing = Direction.NORTH;
		}
		else if(ySpeed>0)
		{
			this.isFacing = Direction.SOUTH;
		}
	}

	public Point getPosition() {
		// TODO Auto-generated method stub
		return new Point(this.x,this.y);
	}
	Point test = new Point(0,0);
	Direction collosionDir = null;
	public void collide(Entity obj) {
		if(new Rectangle(this.x,this.y,this.width,this.height).intersects(new Rectangle(obj.x,obj.y,obj.width,obj.height))){
			if(collosionDir==null)
			{
				collosionDir = this.isFacing;
			}
			if(this.isFacing == Direction.EAST)
			{
				test.x = -1;
			}
			else if(this.isFacing == Direction.WEST)
			{
				test.x = 1;
			}			
			if(this.isFacing == Direction.NORTH)
			{
				test.y = -1;
			}
			else if(this.isFacing == Direction.SOUTH)
			{
				test.y = 1;
			}
		}
		else 
		{
			test = new Point(0,0);
			collosionDir = null;
		}
	}

}