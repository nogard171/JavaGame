package engine;

public enum Direction
{
NORTH,SOUTH,WEST,EAST;
	public static int getDirectionValue(Direction dir)
	{
		switch(dir)
		{
			case NORTH:
				return 0;
			case SOUTH:
				return 1;
			case WEST:
				return 2;
			case EAST:
				return 3;
			default:
				return 0;
		}
	}
	public static int getOpositeDirectionValue(Direction dir)
	{
		switch(dir)
		{
			case NORTH:
				return getDirectionValue(SOUTH);
			case SOUTH:
				return getDirectionValue(NORTH);
			case WEST:
				return getDirectionValue(EAST);
			case EAST:
				return getDirectionValue(WEST);
			default:
				return getDirectionValue(NORTH);
		}
	}
}
