

import java.io.Serializable;

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
    public Vector3f	      position	       = null;
    // the rotation around the Y axis of the camera
    private float	      yaw	       = 45f;
    // the rotation around the X axis of the camera
    float		      pitch	       = 306f;

    float height = 0f;

    private int mode = 0;

    public int direction = 0;

    public void setYaw(float amount)
    {
	if (this.yaw > 360)
	{
	    this.yaw -= 360;
	}
	if (this.yaw < 0)
	{
	    this.yaw += 360;
	}
	this.yaw += amount;
    }

    public void setPitch(float amount)
    {
	if (pitch > 360)
	{
	    this.pitch -= 360;
	}
	if (pitch < 0)
	{
	    this.pitch += 360;
	}
	this.pitch += amount;
	if (pitch <= 275&&pitch >= 180)
	{
	    this.pitch = 275;
	}
	else if (pitch <= 180&&pitch >= 90)
	{
	    this.pitch =90;
	}
    }

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

    public Vector2f getFocus()
    {
	return new Vector2f(pitch, yaw);
    }

    // increment the camera's current yaw rotation
    public float getY()
    {
	return -this.position.y;
    }

    public float checkObjectDistance(Vector3f event)
    {
	float x = this.position.x;
	float y = this.position.y;
	float z = this.position.z;

	float eventX = event.getX() * 2;
	float eventY = (-event.getY()) * 2;
	float eventZ = event.getZ() * 2;

	float finalX = eventX - x;
	float finalY = eventY - y;
	float finalZ = eventZ - z;

	float finalXZ = (float) (Math
		.sqrt(Math.pow(finalX, 2) + Math.pow(finalZ, 2)) / 2);

	float finalXYZ = (float) (Math
		.sqrt(Math.pow(finalY, 2) + Math.pow(finalXZ, 2)) / 2);

	float finalDis = finalXYZ;// Math.sqrt(Math.pow(y, 2) +
				  // Math.pow(finalXZ, 2));
	return finalDis;
    }

    public void walkForward(float distance)
    {
	position.x -= distance * (float) Math.sin(Math.toRadians((yaw)));
	position.z += distance * (float) Math.cos(Math.toRadians((yaw)));
    }

    public void walkToward(float distance)
    {
	position.y += distance * (float) Math.tan(Math.toRadians(-pitch));
	position.x -= distance * (float) Math.sin(Math.toRadians((yaw)));
	position.z += distance * (float) Math.cos(Math.toRadians((yaw)));
    }

    public void walkBackwards(float distance)
    {
	position.x += distance * (float) Math.sin(Math.toRadians(yaw));
	position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
    }

    public void walkAway(float distance)
    {
	position.x += distance * (float) Math.sin(Math.toRadians(yaw));
	position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
	position.y -= distance * (float) Math.tan(Math.toRadians(-pitch));
    }

    public void flyUp(float distance)
    {
	position.y -= distance;

    }

    public void flyDown(float distance)
    {
	position.y += distance;
    }

    // strafes the camera left relitive to its current rotation (yaw)
    public void strafeLeft(float distance)
    {
	position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
	position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));

    }

    // strafes the camera right relitive to its current rotation (yaw)
    public void strafeRight(float distance)
    {
	position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
	position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));

    }

    // translates and rotate the matrix so that it looks through the camera
    // this dose basic what gluLookAt() does
    public void lookThrough()
    {
	direction = (int) (yaw % 360);
	// roatate the pitch around the Y axis
	GL11.glRotatef(-pitch, 1f, 0.0f, 0);
	// roatate the yaw around the X axis
	GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
	// translate to the position vector's location
	GL11.glTranslatef(position.x, position.y + height, position.z);
    }

    public void lookAt()
    {
	direction = (int) (yaw % 360);
	// roatate the pitch around the Y axis
	GL11.glRotatef(-pitch, 1f, 0.0f, 0);
	// roatate the yaw around the X axis
	GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
	// translate to the position vector's location
	GL11.glTranslatef(position.x, position.y + height, position.z);
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

    int jumps = 0;

    public void jump(float i)
    {
	position.y -= i;
	jumps += 1;
    }
}