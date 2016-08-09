import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

public class Tree {
	Sprite sprite = new Sprite(32, 128);
	ArrayList<Leaf> leaves = new ArrayList<Leaf>();
	int x = 0;
	int y = 0;
	int[][] leaf_field = { 
			{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			{ 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 }};

	public Tree() {
		Init_Leaves();
	}

	public Tree(int i, int j) {
		this.x = i;
		this.y = j;
		Init_Leaves();
	}

	Dimension leaf_field_size = new Dimension(20, 20);

	public void Init_Leaves() {
		for (int Y = 0; Y < leaf_field_size.height; Y++) {
			for (int X = 0; X < leaf_field_size.width; X++) {

				if (leaf_field[((leaf_field_size.height-1)-Y)][X] == 1) {
					Leaf leaf = new Leaf(4, 8);
					leaf.x = X * 6;
					leaf.y = Y * 8;
					leaf.sprite.rot = 180;
					leaves.add(leaf);
				}
			}
		}

	}

	int wind = 0;
	boolean blow = false;

	public void Render() {
		if (blow) {
			wind--;
		} else {
			wind++;
		}

		if (wind > 30) {
			blow = true;
		} else if (wind < -30) {
			blow = false;
		}

		sprite.Render(this.x, this.y + this.sprite.height);
		for (Leaf leaf : leaves) {
			//leaf.sprite.rot = wind;
			leaf.Render(this.x - ((leaf_field_size.width / 2) * 4),
					this.y + this.sprite.height - ((leaf_field_size.height / 2) * 6));
		}
	}
}
