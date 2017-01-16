import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class EntityController
{
	public static void MoveEntity(Entity entity, Vector3f speed)
	{
		if (entity != null)
		{
			float x = (speed.getX() * 32) - (speed.getY() * 32);
			float y = -(speed.getY() * 16) - (speed.getX() * 16) + (speed.getZ() * 8);
			entity.setPosition(entity.getPosition().getX() + x, entity.getPosition().getY() + y);
		}
	}
}
