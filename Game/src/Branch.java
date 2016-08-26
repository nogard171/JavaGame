import java.awt.Dimension;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;

public class Branch {
	Object sprite = new Object(8, 48);
	ArrayList<Leaf> leaves = new ArrayList<Leaf>();
	int x = 0;
	int y = 0;

	Dimension leaf_field_size = new Dimension(20, 20);

	public Branch() {
		this.initLeaves();
		this.sprite.color = new Color(255, 128, 0);
		sprite.rot = 45;
		this.sprite.origin = new Point(0, 48);
	}

	public void initLeaves() {
		/*
		 * for (int Y = 0; Y < leaf_field_size.height; Y++) { for (int X = 0; X
		 * < leaf_field_size.width; X++) {
		 * 
		 * Leaf leaf = new Leaf(4, 8); leaf.x = X * 6; leaf.y = Y * 8;
		 * leaf.sprite.rot = 180; leaves.add(leaf);
		 * 
		 * } }
		 */
		Leaf leaf = new Leaf(4, 8);
		// leaf.x = -32;
		// leaf.y = 0;
		// leaf.sprite.rot = 180;
		// leaves.add(leaf);
		int num_Leaves = this.sprite.height / 6;

	}

	int wind = 0;
	boolean blow = false;

	public void Render(int X, int Y) {
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

		// sprite.Render(this.x + X, this.y + Y + this.sprite.height);

		for (Leaf leaf : leaves) {
			// leaf.sprite.rot = wind;
			// leaf.Render(this.x + X, this.y + Y + this.sprite.height);
		}
	}
}
