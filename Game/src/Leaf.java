import java.util.ArrayList;

public class Leaf {
	Sprite sprite = new Sprite(8,16);
	int x = 0;
	int y = 0;
	public Leaf(int i, int j) {
		sprite.setSize(i, j);
	}
	public void Render(int X,int Y)
	{
		//sprite.Render(X+this.x, Y+this.y);
	}
}
