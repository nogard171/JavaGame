import java.awt.Point;

public class Person extends Entity {
	Sprite right_leg = new Sprite(8, 32);
	Sprite left_leg = new Sprite(8, 32);
	Sprite right_arm = new Sprite(8, 32);
	Sprite left_arm = new Sprite(8, 32);
	Sprite head = new Sprite(24, 24);
	int right_offset = 8;
	int left_offset = 4;

	public Person() {
		this.offset = new Point(0, 24);
		setOrigin(16, 16);
		setSize(24, 48);
	}

	int rot = -45;
	boolean forward = true;

	@Override
	public void Render() {

		if (dir == Direction.EAST) {
			right_offset = 10;
			left_offset = 6;
		} else if (dir == Direction.WEST) {
			right_offset = 6;
			left_offset = 10;
		}
		if (rot > 45 || rot < -45) {
			forward = !forward;
		}
		if (moving) {
			if (forward) {
				rot += 2 * velocity_rate;
			} else {
				rot -= 2 * velocity_rate;
			}
		} else {
			rot = 0;
		}

		if(dir == Direction.EAST)
		{
			left_punch_rot =0;
			if (point && right_punch_rot <90) {
				right_punch_rot += 5;
			}
			else if (!point && right_punch_rot >0)
			{
				right_punch_rot -= 5;
			}
		}
		else if(dir == Direction.WEST)
		{
			right_punch_rot =0;
			if (point && left_punch_rot >-90) {
				left_punch_rot -= 5;
			}
			else if (!point && left_punch_rot <0)
			{
				left_punch_rot += 5;
			}
		}
		//
		/*
		 * else if(right_punch_rot >0) { right_punch_rot += 5; }
		 */

		left_leg.rot = rot;
		right_arm.rot = rot + right_punch_rot;
		right_leg.rot = -rot;
		left_arm.rot = -rot + left_punch_rot;
		right_leg.Render(x + right_offset, y - this.sprite.height + offset.y + 8);
		right_arm.Render(x + right_offset, y - this.sprite.height + offset.y + 32);
		head.Render(x, y - this.sprite.height + this.sprite.height + head.height + 24);

		super.Render();
		left_arm.Render(x + left_offset, y - this.sprite.height + offset.y + 40);
		left_leg.Render(x + left_offset, y - this.sprite.height + offset.y + 8);
	}

	boolean point = false;
	int left_punch_rot = 0;
	int right_punch_rot = 0;
}
