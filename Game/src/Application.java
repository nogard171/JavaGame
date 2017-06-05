
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.SwingUtilities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public class Application extends GLWindow {
	// the grid boolean, used for render things as lines.

	public static void main(final String[] argv) {
		final Application app = new Application();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				app.CreateWindow();
			}
		});
	}

	@Override
	public void Setup() {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		drawSphere(1000, 1000, 1000, 1000, 1000);

	}

	@Override
	public void Render()
	{
		super.Render();
		 int i, j;
		 int lats = 1000;
		 int longs = 1000;

		GL11.glBegin(GL11.GL_QUAD_STRIP);
		for(i = 400; i <= 600; i++) {
			 for(j = 600; j <= 800; j++) {
				 Vector3f vec3 = points[i][j].vec2;
				 GL11.glVertex3f(vec3.x,vec3.y,vec3.z);
				 Vector3f vec4 = points[i][j].vec1;
				 GL11.glVertex3f(vec4.x,vec4.y,vec4.z);
			 }
		}
		GL11.glEnd();
	}

	@Override
	public void Update(double delta) {
		super.Update(delta);

		if (keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			System.exit(0);
		}
		
	}

	GLPlane[][] points = new GLPlane[10000][10000];

	public void drawSphere(float basex, float basey, float r, int lats, int longs) {
		int i, j;
		float lat0, lat1, z0, z1, zr0, zr1, lng, x, y, prevlat;
		float xc = 1f / lats;
		float yc = 1f / longs;

		for (i = 0; i <= lats; i++) {
			prevlat = i - 1;
			lat0 = (float) (Math.PI * (-0.5f + prevlat / lats));
			z0 = (float) Math.sin(lat0);
			zr0 = (float) Math.cos(lat0);

			lat1 = (float) (Math.PI * (-0.5f + (float) i / lats));
			z1 = (float) Math.sin(lat1);
			zr1 = (float) Math.cos(lat1);

			// GL11.glBegin(GL11.GL_QUAD_STRIP);
			for (j = 0; j <= longs; j++) {
				lng = (float) Math.PI * 2 * (float) (j - 1) / longs;
				x = (float) (Math.cos(lng) * r);
				y = (float) (Math.sin(lng) * r);
				// points[i][j] =

				GLPlane plane = new GLPlane();
				plane.vec1 = new Vector3f((float) (basex + x * zr0), (float) (basey + y * zr0), (float) (r * z0));
				plane.vec2 = new Vector3f((float) (basex + x * zr1), (float) (basey + y * zr1), (float) (r * z1));

				points[i][j] = plane;

				// GL11.glTexCoord2f(xc*j, yc*i);
				// GL11.glNormal3f(basex + x * zr1, basey + y * zr1, r * z1);
				// GL11.glVertex3f(basex + x * zr1, basey + y * zr1, r * z1);

				// GL11.glTexCoord2f(xc*j, yc*prevlat);
				// GL11.glNormal3f(basex + x * zr0, basey + y * zr0, r * z0);
				// GL11.glVertex3f(basex + x * zr0, basey + y * zr0, r * z0);
			}
			// GL11.glEnd();
		}
	}
}