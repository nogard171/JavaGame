
import static org.lwjgl.opengl.GL11.glCallList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import engine.GLAnimator;
import engine.GLAudio;
import engine.GLClickable;
import engine.GLCollider;
import engine.GLColor;
import engine.GLDisplay;
import engine.GLMaterial;
import engine.GLMouse;
import engine.GLObject;
import engine.GLPhysics;
import engine.GLProperty;
import engine.GLRenderer;
import engine.GLScript;
import engine.GLShader;
import engine.GLSize;
import engine.GLSync;
import engine.GLTransform;
import engine.GLView;
import engine.GLWindow;
import network.GLClient;
import network.GLData;
import network.GLMessage;
import network.GLProtocol;
import network.GLServer;
import network.GLSyncTransform;

public class Game extends GLDisplay {
	public void Start() {
		super.Create();

	}

	@Override
	public void Setup() {

		/*
		 * Random r = new Random(); int Low = 1; int High = 10;
		 * 
		 * // the generated grass globjects for (int x = 0; x < 10; x++) { for
		 * (int y = 0; y < 10; y++) { GLPhysics physics3 = new GLPhysics();
		 * GLMaterial mat = new GLMaterial("resources/textures/grass.png");
		 * GLTransform transform = new GLTransform(x * 32, y * 32); GLShader
		 * shader = new GLShader("basic.vert", "basic.frag"); GLRenderer
		 * spriteRenderer = new GLRenderer(); GLObject obj = new GLObject();
		 * obj.setName("Dirt"); obj.AddComponent(transform);
		 * obj.AddComponent(mat); obj.AddComponent(shader);
		 * obj.AddComponent(spriteRenderer); obj.AddComponent(physics3);
		 * //mappedObjects.put(x + "," + y + ",0", obj);
		 * 
		 * int Result = r.nextInt(High - Low) + Low;
		 * 
		 * if (Result == 1) { mat = new
		 * GLMaterial("resources/textures/tree.png");
		 * 
		 * GLObject tree = new GLObject(); tree.setName("tree");
		 * tree.AddComponent(transform); tree.AddComponent(mat);
		 * tree.AddComponent(shader); tree.AddComponent(spriteRenderer);
		 * tree.AddComponent(physics3); mappedObjects.put(x + "," + y + ",1",
		 * tree); }
		 * 
		 * } }
		 */
		GLObject obj = new GLObject();

		// the player globject
		GLMaterial mat = new GLMaterial("resources/textures/guy.png");
		GLTransform transform = new GLTransform(0, 0);
		GLScript script = new GLScript("resources/scripts/main.lua");
		GLShader shader = new GLShader("basic.vert", "basic.frag");
		GLRenderer spriteRenderer2 = new GLRenderer();
		GLAnimator animator = new GLAnimator();
		animator.setSize(new GLSize(32, 64));

		GLPhysics physics = new GLPhysics();
		GLCollider collider = new GLCollider(0, 0, 32, 32);

		GLSync syncTransform = new GLSync(new GLSyncTransform());

		// GLAudio audio = new GLAudio("resources/audio/walking.wav");
		// audio.setVolume(0.1f);
		/*
		 * GLClickable clickable2 = new GLClickable(); GLProperty health = new
		 * GLProperty(); health.setName("health"); health.setIntValue(100);
		 * 
		 * 
		 * // obj.AddComponent(audio); obj.AddComponent(clickable2);
		 * 
		 * 
		 * obj.AddProperty(health);
		 */

		obj.AddComponent(syncTransform);

		obj.AddComponent(physics);
		obj.AddComponent(collider);

		obj.setName("player");
		obj.AddComponent(transform);
		obj.AddComponent(script);
		obj.AddComponent(mat);
		obj.AddComponent(shader);
		obj.AddComponent(spriteRenderer2);
		obj.AddComponent(animator);

		objects.add(obj);
		// mappedObjects.put("0,0,1", obj);

		// the window globject

		/*
		 * GLMaterial winmat = new GLMaterial("resources/textures/gui.png");
		 * GLTransform wintransform = new GLTransform(200, 200); GLRenderer
		 * winspriteRenderer = new GLRenderer(); GLShader winshader = new
		 * GLShader("window.vert", "window.frag");
		 * 
		 * GLWindow win = new GLWindow(); GLClickable clickable = new
		 * GLClickable("resources/scripts/clickableScript.lua");
		 * 
		 * GLObject window = new GLObject();
		 * 
		 * window.AddComponent(winmat); window.AddComponent(wintransform);
		 * window.AddComponent(winspriteRenderer); window.AddComponent(win);
		 * window.AddComponent(winshader); window.AddComponent(clickable);
		 * 
		 * objects.add(window);
		 */

	}

	ArrayList<GLObject> objects = new ArrayList<GLObject>();
	ArrayList<GLObject> objectsToSync = new ArrayList<GLObject>();
	HashMap<String, GLObject> mappedObjects = new HashMap<String, GLObject>();

	@Override
	public void Update() {
		super.Update();
		if (Display.getWidth() != view.Width || Display.getHeight() != view.Height || view.update) {
			view = new GLView(0, 0, Display.getWidth(), Display.getHeight());
			this.objectInView = getViewObjects();
		}
		for (GLObject obj : objectToUpdate) {
			if (obj != null) {
				GLScript script = (GLScript) obj.getComponent("script");
				GLMaterial mat = (GLMaterial) obj.getComponent("material");
				GLClickable clickable = (GLClickable) obj.getComponent("clickable");
				GLAnimator animator = (GLAnimator) obj.getComponent("animator");
				GLWindow win = (GLWindow) obj.getComponent("window");
				GLPhysics physics = (GLPhysics) obj.getComponent("physics");
				GLCollider collider = (GLCollider) obj.getComponent("collider");
				GLSync sync = (GLSync) obj.getComponent("sync");

				if (physics != null) {
					physics.Run();
				}
				if (collider != null) {
					// collider.Run(objectToUpdate);
				}
				if (win != null) {
					win.Run();
				}
				if (clickable != null) {
					clickable.Run();
				}
				if (script != null) {
					script.Run();
				}
				if (animator != null) {
					animator.Run();
				}

				if (sync != null) {
					sync.Run();
					if (sync.syncNow()) {
						objectsToSync.add(obj);
					}
				}
			}
		}
		objectToUpdate.clear();
		if (client != null && client.started) {
			for (GLObject obj : objectsToSync) {
				GLSync sync = (GLSync) obj.getComponent("sync");
				if (sync != null) {
					GLSyncTransform data = (GLSyncTransform) sync.data;
					if (data != null) {
						// System.out.println(data.position.toString());
						client.sendGLData(data);
					}
				}
			}
		}
		objectsToSync.clear();

		if (Keyboard.isKeyDown(Keyboard.KEY_F1) && server == null) {
			server = new GLServer();
			server.start();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F2) && client == null) {
			client = new GLClient();
			client.start();
		}
	}

	GLServer server = null;
	GLClient client = null;

	@Override
	public void Destroy() {
		for (GLObject obj : objects) {
			obj.Destroy();
		}
		if (server != null) {
			server.destroy();

		}
		super.Destroy();
	}

	GLView view = new GLView(0, 0, 400, 400);

	public ArrayList<GLObject> getViewObjects() {
		ArrayList<GLObject> glViewObjects = new ArrayList<GLObject>();
		for (int z = 0; z < 10; z++) {
			for (int x = (int) Math.floor(view.X / 32); x < Math.ceil(view.Width / 32) + 1; x++) {
				for (int y = (int) Math.floor(view.Y / 32); y < Math.ceil(view.Height / 32) + 1; y++) {

					String key = (x) + "," + y + "," + z;
					GLObject obj = mappedObjects.get(key);
					glViewObjects.add(obj);
				}
			}
		}
		for (GLObject obj : objects) {
			if (view.isObjectInView(obj)) {
				glViewObjects.add(obj);
			}
		}
		return glViewObjects;
	}

	GLMouse mouse = new GLMouse();
	ArrayList<GLObject> objectInView = new ArrayList<GLObject>();
	ArrayList<GLObject> objectToUpdate = new ArrayList<GLObject>();

	@Override
	public void Render() {
		super.Render();
		for (GLObject obj : objects) {
			GLScript script = (GLScript) obj.getComponent("script");
			if (script != null) {
				// script.Run();
				objectToUpdate.add(obj);
			}
		}
		for (GLObject obj : objectInView) {
			if (obj != null) {
				Boolean updateObject = false;
				GLMaterial mat = (GLMaterial) obj.getComponent("material");
				GLTransform transform = (GLTransform) obj.getComponent("transform");
				GLScript script = (GLScript) obj.getComponent("script");
				GLShader shader = (GLShader) obj.getComponent("shader");
				GLRenderer spriteRenderer = (GLRenderer) obj.getComponent("renderer");
				GLAnimator animator = (GLAnimator) obj.getComponent("animator");
				GLWindow win = (GLWindow) obj.getComponent("window");
				GLClickable clickable = (GLClickable) obj.getComponent("clickable");
				GLPhysics physics = (GLPhysics) obj.getComponent("physics");
				GLCollider collider = (GLCollider) obj.getComponent("collider");

				if (collider != null) {
					collider.Run(objectToUpdate);
				}

				if (win != null || clickable != null || script != null || animator != null || physics != null
						|| collider != null) {
					updateObject = true;
				}
				if (updateObject) {
					objectToUpdate.add(obj);
				}
				if (shader != null) {
					shader.Run();
					if (mat != null) {
						GLColor color = mat.getColorAsFloats();
						float[] colorData = { color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() };
						shader.sendUniform4f("vertColor", colorData);
						shader.sendTexture("myTexture", mat.getTextureID());
					}
				}
				if (transform != null) {
					GL11.glPushMatrix();
					GL11.glTranslatef(transform.getPosition().getX() + transform.getCenter().getX(),
							transform.getPosition().getY() + transform.getCenter().getY(), 0);
					GL11.glRotatef(transform.getRotation(), 0, 0, 1);
					GL11.glTranslatef(-transform.getCenter().getX(), -transform.getCenter().getY(), 0);
				}

				spriteRenderer.Run();

				if (shader != null) {
					shader.Stop();
				}

				if (transform != null) {
					GL11.glPopMatrix();
				}
			}
		}

		GL11.glColor3f(0, 0, 0);
	}
}
