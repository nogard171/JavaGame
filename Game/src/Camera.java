
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
import org.lwjgl.input.Mouse;
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
public class Camera implements Serializable {

	private static float maxLook = 85;

	private static float mouseSensitivity = 0.05f;

	private static Vector3f pos;
	private static Vector3f rotation;

	public static void create() {
		pos = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}

	public static void apply() {
		if (rotation.y / 360 > 1) {
			rotation.y -= 360;
		} else if (rotation.y / 360 < -1) {
			rotation.y += 360;
		}
		GL11.glLoadIdentity();
		GL11.glRotatef(rotation.x, mouseSensitivity, 0, 0);
		GL11.glRotatef(rotation.y, 0, mouseSensitivity, 0);
		GL11.glRotatef(rotation.z, 0, 0, mouseSensitivity);
		GL11.glTranslatef(-pos.x, -pos.y, -pos.z);
	}

	public static void acceptInput(float delta) {
		Mouse.poll();
		acceptInputRotate(delta);
		acceptInputGrab();
	}

	public static void move(float speed) {
		pos.x -= Math.sin(Math.toRadians(rotation.y)) * speed;
		pos.z += Math.cos(Math.toRadians(rotation.y)) * speed;
	}

	public static void strafe(float speed) {
		pos.x += Math.sin(Math.toRadians(rotation.y - 90)) * speed;
		pos.z -= Math.cos(Math.toRadians(rotation.y - 90)) * speed;
	}

	public static void fly(float speed) {
		pos.y += speed;
	}

	public static void acceptInputRotate(float delta) {
		if (Mouse.isGrabbed()) {
			float mouseDX = Mouse.getDX();
			float mouseDY = -Mouse.getDY();
			float speed = delta / 50;

			rotation.y += mouseDX * speed;
			rotation.x += mouseDY * speed;
			rotation.x = Math.max(-maxLook, Math.min(maxLook, rotation.x));
		}
	}

	public static void acceptInputGrab() {
		if (Mouse.isInsideWindow() && Mouse.isButtonDown(0)) {
			Mouse.setGrabbed(true);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Mouse.setGrabbed(false);
		}
	}

	public static void setPos(Vector3f pos) {
		Camera.pos = pos;
	}

	public static Vector3f getPos() {
		return pos;
	}

	public static void setX(float x) {
		pos.x = x;
	}

	public static float getX() {
		return pos.x;
	}

	public static void addToX(float x) {
		pos.x += x;
	}

	public static void setY(float y) {
		pos.y = y;
	}

	public static float getY() {
		return pos.y;
	}

	public static void addToY(float y) {
		pos.y += y;
	}

	public static void setZ(float z) {
		pos.z = z;
	}

	public static float getZ() {
		return pos.z;
	}

	public static void addToZ(float z) {
		pos.z += z;
	}

	public static void setRotation(Vector3f rotation) {
		Camera.rotation = rotation;
	}

	public static Vector3f getRotation() {
		return rotation;
	}

	public static void setRotationX(float x) {
		rotation.x = x;
	}

	public static float getRotationX() {
		return rotation.x;
	}

	public static void addToRotationX(float x) {
		rotation.x += x;
	}

	public static void setRotationY(float y) {
		rotation.y = y;
	}

	public static float getRotationY() {
		return rotation.y;
	}

	public static void addToRotationY(float y) {
		rotation.y += y;
	}

	public static void setRotationZ(float z) {
		rotation.z = z;
	}

	public static float getRotationZ() {
		return rotation.z;
	}

	public static void addToRotationZ(float z) {
		rotation.z += z;
	}

	public static void setMaxLook(float maxLook) {
		Camera.maxLook = maxLook;
	}

	public static float getMaxLook() {
		return maxLook;
	}

	public static void setMouseSensitivity(float mouseSensitivity) {
		Camera.mouseSensitivity = mouseSensitivity;
	}

	public static float getMouseSensitivity() {
		return mouseSensitivity;
	}

}