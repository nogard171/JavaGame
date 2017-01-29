import org.lwjgl.util.vector.Vector2f;

public class EntityController
{
	public static void moveEntity(Entity entity, Vector2f speed)
	{
		if (entity != null)
		{
			entity.setPosition(new Vector2f(entity.getPosition().x + speed.x, entity.getPosition().y + speed.y));
		}
	}
}
