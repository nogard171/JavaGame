package core;

import java.awt.Dimension;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class World {

	private Dimension worldSize = new Dimension(16, 16);

	HashMap<String, Entity> groundObjects = new HashMap<String, Entity>();
	HashMap<String, Entity> maskObjects = new HashMap<String, Entity>();
	HashMap<String, Entity> playerObjects = new HashMap<String, Entity>();

	public void generateGround() {
		for (int x = 0; x < worldSize.getWidth(); x++) {
			for (int z = 0; z < worldSize.getHeight(); z++) {
				String newKey = x + "," + z;
				Entity newObject = new Entity();
				newObject.setPosition(new Vector3f(x, 0, z));
				if (x % 2 == 0) {
					newObject.setType(Type.GRASS);
					newObject.setMaterialName("grass");
				} else {
					newObject.setType(Type.DIRT);
					newObject.setMaterialName("dirt");
				}

				groundObjects.put(newKey, newObject);
			}
		}
	}

	public void render(Camera camera) {
		for (int x = 0; x < worldSize.getWidth(); x++) {
			for (int z = 0; z < worldSize.getHeight(); z++) {
				String newKey = x + "," + z;
				Entity newObject = groundObjects.get(newKey);

				Renderer.renderObject(newObject);
			}
		}
	}

}
