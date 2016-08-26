import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

public class Tree {
	Object sprite = new Object(32, 128);
	ArrayList<Branch> branches = new ArrayList<Branch>();
	int x = 0;
	int y = 0;

	public Tree() {
		initBranches();
	}

	public Tree(int i, int j) {
		this.x = i;
		this.y = j;
		initBranches();
	}

	Dimension leaf_field_size = new Dimension(20, 20);

	public void initBranches() {
		Branch branch = new Branch();
		branches.add(branch);
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
		sprite.setPosition(this.x, this.y + this.sprite.height);
		sprite.Render();
		for (Branch branch : branches) {
			branch.Render(this.x, this.y + this.sprite.height);
		}
	}
}
