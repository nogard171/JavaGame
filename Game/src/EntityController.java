import org.lwjgl.util.vector.Vector3f;

public class EntityController
{
	public static void move(Entity entity, Vector3f direction)
	{
		increaseX(entity, direction.x);
		increaseY(entity, direction.y);
		increaseZ(entity, direction.z);
	}

	public static void increaseX(Entity entity, float x)
	{
		entity.x += x;
	}

	public static void increaseY(Entity entity, float y)
	{
		entity.y += y;
	}

	public static void increaseZ(Entity entity, float z)
	{
		entity.z += z;
	}

	public void setPosition(Entity entity, float x, float y, float z)
	{
		entity.x = x;
		entity.y = y;
		entity.z = z;
	}
}
