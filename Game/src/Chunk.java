import org.lwjgl.opengl.GL11;

public class Chunk {

	float[][][] cubes;
	
	public Chunk()
	{
		cubes = new float[16][16][16];
		
		for(int y=0;y<cubes.length;y++)
		{
			for(int x=0;x<cubes[y].length;x++)
			{
				for(int z=0;z<cubes[y][x].length;z++)
				{
					cubes[y][x][z] = 1;
				}
			}
		}
	}

	public void renderChunk() {
		//GL11.glPushMatrix();
		//GL11.glTranslatef(0,0,0);
		for (int y = 0; y < cubes.length; y++) {
			for (int x = 0; x < cubes[y].length; x++) {
				for (int z = 0; z < cubes[y][x].length; z++) {
					float data = cubes[x][y][z];
					if(data>-1)
					{
						renderCube(x*2, y*2, z*2);
					}
				}
			}
		}
		//test
		//GL11.glPopMatrix();
	}

	public void renderCube(float x, float y, float z) {
		GL11.glBegin(GL11.GL_QUADS);
		//bottom
		//GL11.glColor3f(1,0,0);
		GL11.glVertex3f(x+1, y, z+1);
		GL11.glVertex3f(x, y, z+1);
		GL11.glVertex3f(x, y, z);
		GL11.glVertex3f(x+1, y, z);
		
		//top
		//GL11.glColor3f(1,1,0);
		GL11.glVertex3f(x+1, y+1, z);
		GL11.glVertex3f(x, y+1, z);
		GL11.glVertex3f(x, y+1, z+1);
		GL11.glVertex3f(x+1, y+1, z+1);
		
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
