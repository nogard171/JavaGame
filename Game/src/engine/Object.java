package engine;

import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;

public class Object
{
	private Vector2f displayListCoords = new Vector2f(0, 0);
	private Bound bounds = new Bound(0, 0, 32, 32);
	private Vector2f spriteOffset = new Vector2f(0, 0);
	private Direction facing = Direction.NORTH;
	private Boolean[] collision =
	{ false, false, false, false };
	private Boolean colliding = false;

	public Boolean getCollisionByDirection(Direction dir)
	{
		return collision[Direction.getDirectionValue(dir)];
	}

	public void setCollisionByDirection(Direction dir, boolean value)
	{
		boolean alreadyColliding = false;
		for (int c = 0; c < collision.length; c++)
		{
			if (collision[c])
			{
				alreadyColliding = true;
				break;
			}
		}
		if (!alreadyColliding)
		{
			collision[Direction.getDirectionValue(dir)] = value;
			setColliding(value);
		}
	}

	public void clearCollision()
	{
		for (int c = 0; c < collision.length; c++)
		{
			collision[c] = false;
		}
		setColliding(false);
	}

	public Direction getFacing()
	{
		return facing;
	}

	public void setFacing(Direction facing)
	{
		this.facing = facing;
	}

	public Point getPosition()
	{
		return new Point((int) this.getBounds().getX(), (int) this.getBounds().getY());
	}

	public Vector2f getDisplayListCoords()
	{
		return displayListCoords;
	}

	public void setDisplayListCoords(Vector2f displayListCoords)
	{
		this.displayListCoords = displayListCoords;
	}

	public Bound getBounds()
	{
		return bounds;
	}

	public void setBounds(Bound bounds)
	{
		this.bounds = bounds;
	}

	public Vector2f getSpriteOffset()
	{
		return spriteOffset;
	}

	public void setSpriteOffset(Vector2f spriteOffset)
	{
		this.spriteOffset = spriteOffset;
	}

	public void setPosition(int x, int y)
	{
		this.bounds = new Bound(x, y, this.bounds.getWidth(), this.bounds.getHeight());
	}

	public Boolean getColliding()
	{
		return colliding;
	}

	public void setColliding(Boolean colliding)
	{
		this.colliding = colliding;
	}
}
