
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.io.Serializable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

// First Person Camera Controller
public class Camera implements Serializable
{

	private static final long serialVersionUID = 5950169519310163575L;
	// 3d vector to store the camera's position in
	public Vector3f position = null;
	public Vector3f getPosition()
	{
		return new Vector3f(position.getX(),position.getY(),position.getZ());
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	// the rotation around the Y axis of the camera
	private float yaw = 140f;
	// the rotation around the X axis of the camera
	float pitch = 0f;
	
	float roll = 0f;

	float height = 0f;

	private int mode = 0;

	public int direction = 0;
	float speed_Factor = 1;

	// Constructor that takes the starting x, y, z location of the camera
	public Camera(float x, float y, float z)
	{
		// instantiate position Vector3f to the x y z params.
		position = new Vector3f(x, y, z);
	}

	public Camera(int x, int y, int z, int p, int newYaw)
	{
		// TODO Auto-generated constructor stub
		position = new Vector3f(x, y, z);
		pitch = p;
		yaw = newYaw;
	}

	public void setYaw(float amount)
	{
		/*f (this.yaw > 360)
		{
			this.yaw -= 360;
		}
		if (this.yaw < 0)
		{
			this.yaw += 360;
		}*/
		this.yaw += amount;
	}

	public void setPitch(float amount)
	{
		/*if (pitch > 360)
		{
			this.pitch -= 360;
		}
		if (pitch < 0)
		{
			this.pitch += 360;
		}*/
		this.pitch += amount;
		/*if (pitch <= 275 && pitch >= 180)
		{
			this.pitch = 275;
		} else if (pitch <= 180 && pitch >= 90)
		{
			this.pitch = 90;
		}*/
	}

	// increment the camera's current yaw rotation
	public float getY()
	{
		return this.position.y;
	}

	public void walk(float distance)
	{
		distance = distance / speed_Factor;
		position.x -= distance * (float) Math.sin(Math.toRadians((yaw)));
		position.z += distance * (float) Math.cos(Math.toRadians((yaw)));
	}

	public void walkToward(float distance)
	{
		position.y -= distance * (float) Math.tan(Math.toRadians(-pitch));
		position.x += distance * (float) Math.sin(Math.toRadians((yaw)));
		position.z -= distance * (float) Math.cos(Math.toRadians((yaw)));
	}

	public void walkAway(float distance)
	{
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw));
		position.y += distance * (float) Math.tan(Math.toRadians(-pitch));
	}

	public void fly(float distance)
	{
		position.y -= distance;

	}

	// strafes the camera left relitive to its current rotation (yaw)
	public void strafe(float distance)
	{
		distance = distance / speed_Factor;
		position.x += distance * (float) Math.sin(Math.toRadians(yaw - 90));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw - 90));

	}

	// translates and rotate the matrix so that it looks through the camera
	// this dose basic what gluLookAt() does
	public void lookThrough()
	{
		//direction = (int) (yaw % 360);
		// roatate the pitch around the Y axis
		GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		

		// roatate the yaw around the X axis
		GL11.glRotatef(-pitch, 1f, 0.0f, 0);
		

		//roatate the pitch around the Z axis
		GL11.glRotatef(yaw, 0.0f, 0f, 1.0f);
		// translate to the position vector's location
		GL11.glTranslatef(-position.x, -position.y, -position.z);
	}

	public void lookAtVector3f(Vector3f vec)
	{
		double dx = this.position.x-vec.x;
		double dz = this.position.z-vec.z;
		
		double d = Math.sqrt((dx*2)+(dz*2));
		
		double rotAng = Math.toDegrees(Math.asin(dx/d));
		//System.out.println(dz+"/"+dx + "/" + rotAng);
		// roatate the pitch around the Y axis
		GL11.glRotatef(-pitch, 1f, 0.0f, 0);
		// roatate the yaw around the X axis
		GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);

		GL11.glRotatef(-roll, 0.0f, 0.0f, 1.0f);

		// translate to the position vector's location
		GL11.glTranslatef(-position.x, -position.y, -position.z);
	}


	public void rotateY(float degrees)
	{
		pitch += degrees;
	}

	public void rotateX(float degrees)
	{
		yaw += degrees;
	}

	public float getYaw()
	{
		return this.yaw;
	}

	public float getPitch()
	{
		return this.pitch;
	}

}