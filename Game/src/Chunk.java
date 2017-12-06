import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class Chunk {

	Material[][][] cubes;
	
	public Chunk()
	{
		cubes = new Material[16][16][16];
		
		for(int y=0;y<cubes.length;y++)
		{
			for(int x=0;x<cubes[y].length;x++)
			{
				for(int z=0;z<cubes[y][x].length;z++)
				{
					cubes[y][x][z] = new Material();
				}
			}
		}
	}

	public void renderChunk(float step) {
		//GL11.glPushMatrix();
		//GL11.glTranslatef(0,0,0);
		for (int y = 0; y < cubes.length; y++) {
			for (int x = 0; x < cubes[y].length; x++) {
				for (int z = 0; z < cubes[y][x].length; z++) {
					Material mat = cubes[y][x][z];
					if(mat!=null)
					{
						renderCube(new Vector3f(x*2, y*2, z*2),step,mat);
					}
				}
			}
		}
		//test
		//GL11.glPopMatrix();
	}

	public void renderCube(Vector3f vec,float step,Material mat) {
		float x = vec.x;
		float y =vec.y;
		float z = vec.z;
		GL11.glBegin(GL11.GL_QUADS);
		
		Vector2f tex = mat.vec[0];
		//bottom
		GL11.glTexCoord2f(tex.x,tex.y);
		GL11.glVertex3f(x+1, y, z+1);
		GL11.glTexCoord2f(tex.x+step, tex.y);
		GL11.glVertex3f(x, y, z+1);
		GL11.glTexCoord2f(tex.x+step,tex.y+step);
		GL11.glVertex3f(x, y, z);
		GL11.glTexCoord2f(tex.x,tex.y+step);
		GL11.glVertex3f(x+1, y, z);
		
		tex = mat.vec[1];
		//top
		GL11.glTexCoord2f((tex.x)*step,(tex.y)*step);
		GL11.glVertex3f(x+1, y+1, z);
		GL11.glTexCoord2f((tex.x+1)*step, (tex.y)*step);
		GL11.glVertex3f(x, y+1, z);
		GL11.glTexCoord2f((tex.x+1)*step,(tex.y+1)*step);
		GL11.glVertex3f(x, y+1, z+1);
		GL11.glTexCoord2f((tex.x)*step,(tex.y+1)*step);
		GL11.glVertex3f(x+1, y+1, z+1);
		
		tex = mat.vec[2];
		//south
		//GL11.glColor3f(0,1,1);
		GL11.glVertex3f(x+1, y, z);
		GL11.glVertex3f(x, y, z);
		GL11.glVertex3f(x, y+1, z);
		GL11.glVertex3f(x+1, y+1, z);

		//north
		//GL11.glColor3f(1,0,1);
		GL11.glVertex3f(x, y, z+1);
		GL11.glVertex3f(x+1, y, z+1);
		GL11.glVertex3f(x+1, y+1, z+1);
		GL11.glVertex3f(x, y+1, z+1);
		
		//east
		//GL11.glColor3f(0,1,0);
		GL11.glVertex3f(x+1, y, z+1);
		GL11.glVertex3f(x+1, y, z);
		GL11.glVertex3f(x+1, y+1, z);
		GL11.glVertex3f(x+1, y+1, z+1);
		
		//west
		//GL11.glColor3f(0,0,1);
		GL11.glVertex3f(x, y, z);
		GL11.glVertex3f(x, y, z+1);
		GL11.glVertex3f(x, y+1, z+1);
		GL11.glVertex3f(x, y+1, z);
		
		GL11.glEnd();
	}
}
