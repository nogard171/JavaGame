package core;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class CameraController {
	private Camera camera;
	private float maxLook = 85;
	private float cameraDistance = 20;
	private float mouseSensitivity = 0.05f;

	public CameraController(Camera newCamera) {
		this.camera = newCamera;
		// this.camera.setPosition(new Vector3f(0, 20, 10));
	}

	public void setXRotation(float newX) {
		this.camera.setRotation(new Vector3f(newX, this.camera.getRotation().getY(), this.camera.getRotation().getZ()));
	}

	public void setYRotation(float newY) {
		this.camera.setRotation(new Vector3f(this.camera.getRotation().getX(), newY, this.camera.getRotation().getZ()));
	}

	public void setZRotation(float newZ) {
		this.camera.setRotation(new Vector3f(this.camera.getRotation().getX(), this.camera.getRotation().getY(), newZ));
	}

	public float getMouseSensitivity() {
		return this.mouseSensitivity;
	}

	public void setMouseSensitivity(float mouseSensitivity) {
		this.mouseSensitivity = mouseSensitivity;
	}

	public void addDistance(float newAmount) {
		this.cameraDistance += newAmount;
	}

	public void apply() {
		if (this.camera.getRotation().getY() / 360 > 1) {
			this.setYRotation(-360);
		} else if (this.camera.getRotation().getY() / 360 < -1) {
			this.setYRotation(360);
		}
		GL11.glLoadIdentity();
		GL11.glTranslatef(0, 0, -cameraDistance);
		GL11.glRotatef(this.camera.getRotation().getY(), mouseSensitivity, 0, 0);
		GL11.glRotatef(this.camera.getRotation().getX(), 0, mouseSensitivity, 0);
		GL11.glRotatef(this.camera.getRotation().getZ(), 0, 0, mouseSensitivity);

		GL11.glTranslatef(0, 0, 0);
	}

	public void update(float delta) {
		Mouse.poll();
		float mouseDX = Mouse.getDX();
		float mouseDY = -Mouse.getDY();
		float speed = delta / 5;

		// this.addYRotation(mouseDY * speed);
		// this.addXRotation(mouseDX * speed);
	}

	private void addYRotation(float newY) {
		this.camera.setRotation(new Vector3f(this.camera.getRotation().getX(), this.camera.getRotation().getY() + newY,
				this.camera.getRotation().getZ()));

	}

	private void addXRotation(float newX) {
		this.camera.setRotation(new Vector3f(this.camera.getRotation().getX() + newX, this.camera.getRotation().getY(),
				this.camera.getRotation().getZ()));

	}

	private void addZRotation(float newZ) {
		this.camera.setRotation(new Vector3f(this.camera.getRotation().getX(), this.camera.getRotation().getY(),
				this.camera.getRotation().getZ() + newZ));

	}

	private void addX(float newX) {
		this.camera.setPosition(new Vector3f(this.camera.getPosition().getX() + newX, this.camera.getPosition().getY(),
				this.camera.getPosition().getZ()));
	}

	private void addY(float newY) {
		this.camera.setPosition(new Vector3f(this.camera.getPosition().getX(), this.camera.getPosition().getY() + newY,
				this.camera.getPosition().getZ()));
	}

	private void addZ(float newZ) {
		this.camera.setPosition(new Vector3f(this.camera.getPosition().getX(), this.camera.getPosition().getY(),
				this.camera.getPosition().getZ() + newZ));
	}

	public void move(float speed) {
		this.addX((float) -(Math.sin(Math.toRadians(this.camera.getRotation().getX())) * speed));
		this.addZ((float) Math.cos(Math.toRadians(this.camera.getRotation().getX())) * speed);
	}

	public void strafe(float speed) {
		this.addX((float) Math.sin(Math.toRadians(this.camera.getRotation().getX() - 90)) * speed);
		this.addZ((float) -(Math.cos(Math.toRadians(this.camera.getRotation().getX() - 90)) * speed));
	}

	public void rotateX(float speed) {
		this.addXRotation(speed);
	}

	public void rotateY(float speed) {
		if (this.camera.getRotation().getY() >= 20 && this.camera.getRotation().getY() <= 70) {
			this.addYRotation(speed);
		}

		if (this.camera.getRotation().getY() < 20) {
			this.setYRotation(20);
		}
		if (this.camera.getRotation().getY() > 70) {
			this.setYRotation(70);
		}
	}
}
