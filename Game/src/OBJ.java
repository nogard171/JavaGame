import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class OBJ {
	ArrayList<Vector3f> vectors = new ArrayList<Vector3f>();
	ArrayList<Vector4f> faces = new ArrayList<Vector4f>();
	ArrayList<Vector4f> textureFaces = new ArrayList<Vector4f>();
	ArrayList<Vector2f> textureVectors = new ArrayList<Vector2f>();
	String textureLocation = "";
	String mtlFile = "";
	String objectName = "";
	String textureFile = "";
	Texture texture;

	public void setData()
	{
		
	}
	float x = 0;
	float y = 0;
	float z = 0;
	public float yaw = 0;

	int[] render_faces = { 0, 1, 2, 3, 4, 5 };

	public void renderOBJ(int[] sides, int terrainMode) {
		if(sides[0]==0)
		{
			render_faces[4]=-1;
		}
		else
		{
			render_faces[4]=4;
		}
		if(sides[1]==0)
		{
			render_faces[2]=-1;
		}
		else
		{
			render_faces[2]=2;
		}
		if(sides[2]==0)
		{
			render_faces[5]=-1;
		}
		else
		{
			render_faces[5]=5;
		}
		if(sides[3]==0)
		{
			render_faces[3]=-1;
		}
		else
		{
			render_faces[3]=3;
		}
		if(sides[4]==0)
		{
			render_faces[0]=-1;
		}
		else
		{
			render_faces[0]=0;
		}
		if(sides[5]==0)
		{
			render_faces[1]=-1;
		}
		else
		{
			render_faces[1]=1;
		}
		renderOBJ(terrainMode);
	}

	public void renderOBJ(int terrainMode) {
		// GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		texture.bind();
		int mode = 0;
		if (terrainMode == GL11.GL_QUADS) {
			mode = GL11.GL_QUADS;
		} else {
			mode = GL11.GL_LINE_STRIP;
		}

		for (int f = 0; f < faces.size(); f++) {
			if (array_has(render_faces, f)) {
				GL11.glColor4f(1f, 1f, 1f, 1);
				GL11.glBegin(mode);
				GL11.glTexCoord2f(
						textureVectors.get((int) textureFaces.get(f).w - 1).x,
						textureVectors.get((int) textureFaces.get(f).w - 1).y);
				GL11.glVertex3f(vectors.get((int) faces.get(f).w - 1).x,
						vectors.get((int) faces.get(f).w - 1).y,
						vectors.get((int) faces.get(f).w - 1).z);
				GL11.glTexCoord2f(
						textureVectors.get((int) textureFaces.get(f).x - 1).x,
						textureVectors.get((int) textureFaces.get(f).x - 1).y);
				GL11.glVertex3f(vectors.get((int) faces.get(f).x - 1).x,
						vectors.get((int) faces.get(f).x - 1).y,
						vectors.get((int) faces.get(f).x - 1).z);
				GL11.glTexCoord2f(
						textureVectors.get((int) textureFaces.get(f).y - 1).x,
						textureVectors.get((int) textureFaces.get(f).y - 1).y);
				GL11.glVertex3f(vectors.get((int) faces.get(f).y - 1).x,
						vectors.get((int) faces.get(f).y - 1).y,
						vectors.get((int) faces.get(f).y - 1).z);
				GL11.glTexCoord2f(
						textureVectors.get((int) textureFaces.get(f).z - 1).x,
						textureVectors.get((int) textureFaces.get(f).z - 1).y);
				GL11.glVertex3f(vectors.get((int) faces.get(f).z - 1).x,
						vectors.get((int) faces.get(f).z - 1).y,
						vectors.get((int) faces.get(f).z - 1).z);
				GL11.glEnd();
			}
		}
		mode = GL11.GL_LINE_STRIP;
		for (int f = 0; f < faces.size(); f++) {
			if (array_has(render_faces, f)) {
			GL11.glColor4f(0f, 0f, 0f, 1);
			GL11.glBegin(mode);
			GL11.glVertex3f(vectors.get((int) faces.get(f).w - 1).x,
					vectors.get((int) faces.get(f).w - 1).y,
					vectors.get((int) faces.get(f).w - 1).z);
			GL11.glVertex3f(vectors.get((int) faces.get(f).x - 1).x,
					vectors.get((int) faces.get(f).x - 1).y,
					vectors.get((int) faces.get(f).x - 1).z);
			GL11.glVertex3f(vectors.get((int) faces.get(f).y - 1).x,
					vectors.get((int) faces.get(f).y - 1).y,
					vectors.get((int) faces.get(f).y - 1).z);
			GL11.glVertex3f(vectors.get((int) faces.get(f).z - 1).x,
					vectors.get((int) faces.get(f).z - 1).y,
					vectors.get((int) faces.get(f).z - 1).z);
			GL11.glEnd();
			}
		}
		GL11.glPopMatrix();
	}

	private boolean array_has(int[] render_faces2, int f) {
		boolean has = false;
		for (int i = 0; i < render_faces.length; i++) {
			if (render_faces[i] == f) {
				has = true;
				break;
			}
		}
		return has;
	}

}
