import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Object extends Sprite{
	String name = "";
	Vector2f position = new Vector2f(0,0);
	
	@Override
	public void Render()
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(position.getX(), position.getY(), 0);
		super.Render();
		GL11.glPopMatrix();
	}
}
