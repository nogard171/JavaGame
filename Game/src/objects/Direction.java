package objects;
//the direction enum
public enum Direction {
	Left,
	Right,
	Up,
	Down;

	public static Direction parseDirection(String string) {
		// TODO Auto-generated method stub
		if(string.toLowerCase().equals("right"))
		{
			return Right;
		}
		else if(string.toLowerCase().equals("left"))
		{
			return Left;
		}
		else if(string.toLowerCase().equals("up"))
		{
			return Up;
		}
		else if(string.toLowerCase().equals("down"))
		{
			return Down;
		}
		else
		{
			return Right;
		}
	}
}