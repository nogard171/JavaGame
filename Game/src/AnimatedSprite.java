import org.lwjgl.util.Dimension;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

public class AnimatedSprite
{
	private int[][] DLIDs = {};
	private int TID = -1;
	public Dimension size = new Dimension(32,64);
	public Point animation = new Point(0,0);
	public int[][] getDLIDs()
	{
		return DLIDs;
	}
	public void setDLIDs(int[][] dLIDs)
	{
		DLIDs = dLIDs;
	}
	public int getTID()
	{
		return TID;
	}
	public void setTID(int tID)
	{
		TID = tID;
	}
	public int getDLID()
	{
		// TODO Auto-generated method stub
		int newDLID = this.DLIDs[0][0];
		
		if(this.DLIDs.length>this.animation.getX()&&this.DLIDs[0].length>this.animation.getY())
		{
			newDLID = this.DLIDs[this.animation.getX()][this.animation.getY()];
		}
		
		return newDLID;
	}
}
