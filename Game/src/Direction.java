import java.awt.Point;

public enum Direction {
	NORTH, SOUTH, EAST, WEST,NONE;
	public Direction ParseDirection(String dir)
	{
		Direction newDir = Direction.NORTH;
		if(dir.toLowerCase() =="east")
		{
			newDir = Direction.EAST;
		}
		if(dir.toLowerCase() =="west")
		{
			newDir = Direction.WEST;
		}
		if(dir.toLowerCase() =="south")
		{
			newDir = Direction.SOUTH;
		}
		return newDir;
	}
}
