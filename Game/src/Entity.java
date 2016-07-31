import java.awt.Point;

public class Entity{
	Sprite sprite = new Sprite();
	int x = 0;
	int y = 0;
	float speed = 1;
	Point offset = new Point(0,0);
	int jump = 0;
	Direction dir = Direction.WEST;
	boolean moving = false;
	public int velocity_rate = 1;
	public Entity(){}
	public Entity(int new_X, int new_Y)
	{
		this.x = new_X;
		this.y = new_Y;
	}
	public void Move(int x_Speed, int y_Speed)
	{
		moving = true;
		if(x_Speed>0)
		{
			dir = Direction.EAST;
		}
		else if(x_Speed<0)
		{
			dir = Direction.WEST;
		}
		
		this.x+=(x_Speed*speed)*velocity_rate;
		this.y+=(y_Speed*speed)*velocity_rate;
	}
	
	public void EndMove()
	{
		moving = false;
	}
	
	public void Jump(float delta)
	{
		
	}
	public void Render()
	{
		sprite.Render(x, y+offset.y);
	}
	public void setSize(int i, int j) {
		// TODO Auto-generated method stub
		sprite.setSize(i, j);
	}
	public void setOrigin(int i, int j) {
		// TODO Auto-generated method stub
		sprite.origin = new Point(i,j);
	}
	public int getHeight() {
		// TODO Auto-generated method stub
		return sprite.height;
	}
}
