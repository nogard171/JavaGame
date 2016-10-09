import java.awt.event.MouseEvent;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Object extends Sprite {

	Rectangle position = new Rectangle(0, 0, 32, 32);

	Point offset = new Point(0, 0);
	boolean isSolid = false;
	int rot = 0;
	boolean render = true;

	int mouseClicked = 0;
	public boolean isHovered = false;

	public Object(Type newType) {
		super(32, 32);
		this.type = newType;
	}

	public Object() {
		super(32, 32);
	}

	public Object(int width, int height) {
		super(width, height);
	}

	public void setPosition(int x, int y) {
		this.position = new Rectangle(x, y, position.getWidth(), position.getHeight());
	}

	public void Render() {
		if (this.type == Type.BLANK) {
			render = false;
		} 
		if (render) {
			GL11.glPushMatrix();
			GL11.glTranslatef(this.position.getX() + origin.getX() + offset.getX(),
					this.position.getY() - origin.getY() + offset.getY(), 0);
			GL11.glRotatef(rot, 0, 0, 1);
			GL11.glTranslatef(-origin.getX(), origin.getY(), 0);
			super.Render();
			GL11.glTranslatef(origin.getX(), -origin.getY(), 0);
			GL11.glTranslatef(-this.position.getX() - origin.getX() - offset.getX(),
					-this.position.getY() + origin.getY() - offset.getY(), 0);
			GL11.glPopMatrix();
		}

	}


}