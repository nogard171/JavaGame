package engine;

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
public class GLCamera {

	public static float moveSpeed = 0.005f;

	private static float maxLook = 85;

	private static float mouseSensitivity = 0.25f;

	private static Vector3f pos;
	private static Vector3f rotation;

	public GLCamera() {
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
		GL11.glRotatef(rotation.x, 10, 0, 0);
		GL11.glRotatef(rotation.y, 0, 10, 0);
		GL11.glRotatef(rotation.z, 0, 0, 10);
		GL11.glTranslatef(-pos.x, -pos.y, -pos.z);
		
		
	}

	public void poll(float delta) {
		if (Mouse.isGrabbed()) {
			float mouseDX = Mouse.getDX();
			float mouseDY = -Mouse.getDY();
			rotation.y += mouseDX * mouseSensitivity * delta;
			rotation.x += mouseDY * mouseSensitivity * delta;
			rotation.x = Math.max(-maxLook, Math.min(maxLook, rotation.x));
			
			//Display.setTitle("Rot:" + rotation);
		}
	}

	public static void acceptInputMove(float delta) {
		boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_D);
		boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean keyFast = Keyboard.isKeyDown(Keyboard.KEY_Q);
		boolean keySlow = Keyboard.isKeyDown(Keyboard.KEY_E);
		boolean keyFlyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		boolean keyFlyDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

		float speed;

		if (keyFast) {
			speed = moveSpeed * 5;
		} else if (keySlow) {
			speed = moveSpeed / 2;
		} else {
			speed = moveSpeed;
		}

		speed *= delta;

		if (keyFlyUp) {
			pos.y += speed;
		}
		if (keyFlyDown) {
			pos.y -= speed;
		}

		if (keyDown) {
			pos.x -= Math.sin(Math.toRadians(rotation.y)) * speed;
			pos.z += Math.cos(Math.toRadians(rotation.y)) * speed;
		}
		if (keyUp) {
			pos.x += Math.sin(Math.toRadians(rotation.y)) * speed;
			pos.z -= Math.cos(Math.toRadians(rotation.y)) * speed;
		}
		if (keyLeft) {
			pos.x += Math.sin(Math.toRadians(rotation.y - 90)) * speed;
			pos.z -= Math.cos(Math.toRadians(rotation.y - 90)) * speed;
		}
		if (keyRight) {
			pos.x += Math.sin(Math.toRadians(rotation.y + 90)) * speed;
			pos.z -= Math.cos(Math.toRadians(rotation.y + 90)) * speed;
		}
	}
}