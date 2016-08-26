import org.lwjgl.util.Point;
import org.newdawn.slick.Color;

public class Person extends Entity {
	Object right_leg = new Object(8, 32);
	Object left_leg = new Object(8, 32);

	Object right_arm = new Object(8, 32);
	Object left_arm = new Object(8, 32);

	Object head = new Object(24, 24);
	int right_offset_x = 8;
	int left_offset_x = 4;
	int left_offset_y = 40;
	int right_offset_y = 40;

	int move = 0;

	boolean is_left_handed = false;

	public Person() {

		super.origin = new Point(0, 24);
		super.setOrigin(16, 16);
		setSize(24, 48);

		left_arm.origin = new Point(left_arm.width / 2, left_arm.width / 2);
		right_arm.origin = new Point(right_arm.width / 2, right_arm.width / 2);

		left_leg.origin = new Point(left_leg.width / 2, left_leg.width / 2);
		right_leg.origin = new Point(right_leg.width / 2, right_leg.width / 2);
		if (is_left_handed) {
			right_offset_x = 8;
			left_offset_x = 4;
		} else {
			right_offset_x = -8;
			left_offset_x = -32;
		}
	}

	int rot = -45;
	boolean forward = true;

	@Override
	public void Update() {
		if (dir == Direction.EAST) {
			right_offset_x = 10;
			left_offset_x = 6;
		} else if (dir == Direction.WEST) {
			right_offset_x = 6;
			left_offset_x = 10;
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

		if (dir == Direction.EAST) {

			if (is_left_handed) {
				left_punch_rot = 0;
				if (point && right_punch_rot < 90) {

					right_punch_rot += 5;
				} else if (!point && right_punch_rot > 0) {
					right_punch_rot -= 5;
				}

			} else {
				right_punch_rot = 0;
				if (point && left_punch_rot < 90) {
					left_punch_rot += 5;
				} else if (!point && left_punch_rot > 0) {
					left_punch_rot -= 5;
				}
			}
		} else if (dir == Direction.WEST) {
			if (!is_left_handed) {
				left_punch_rot = 0;
				if (point && right_punch_rot > -90) {
					right_punch_rot -= 5;

				} else if (!point && right_punch_rot < 0) {
					right_punch_rot += 5;
				}

			} else {
				right_punch_rot = 0;
				if (point && left_punch_rot > -90) {
					left_punch_rot -= 5;
				} else if (!point && left_punch_rot < 0) {
					left_punch_rot += 5;
				}
			}
		}
	}

	@Override
	public void Render() {

		//
		/*
		 * else if(right_punch_rot >0) { right_punch_rot += 5; }
		 */

		left_leg.rot = rot;
		right_arm.rot = rot + right_punch_rot;
		right_leg.rot = -rot;
		left_arm.rot = -rot + left_punch_rot;
		right_leg.setPosition(super.position.getX() + right_offset_x,
				super.position.getY() - super.height + origin.getY() + 16);
		right_leg.Render();
		right_arm.setPosition(super.position.getX() + right_offset_x,
				super.position.getY() - super.height + origin.getY() + 48);
		right_arm.Render();
		head.setPosition(super.position.getX(), super.position.getY() - super.height + super.height + head.height + 24);
		head.Render();
		super.offset = new Point(0, 24);
		super.Render();
		left_leg.setPosition(super.position.getX() + left_offset_x,
				super.position.getY() - super.height + origin.getY() + 16);
		left_leg.Render();
		left_arm.setPosition(super.position.getX() + left_offset_x,
				super.position.getY() - super.height + origin.getY() + 48);
		left_arm.Render();
	}

	boolean point = false;
	int left_punch_rot = 0;
	int right_punch_rot = 0;

	@Override
	public void setColor(Color newColor) {
		super.color = newColor;
		left_arm.setColor(newColor);
		left_leg.setColor(newColor);
		right_arm.setColor(newColor);
		right_leg.setColor(newColor);
		head.setColor(newColor);
	}
}
