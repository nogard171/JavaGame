import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

public class GLDataHub {
	public static GLCamera camera = null;

	public static Texture texture = null;
	public static HashMap<String, GLModel> models = new HashMap<String, GLModel>();
	public static GLShader shader;
	public static ArrayList<GLObject> objects = new ArrayList<GLObject>();
	public static ArrayList<GLObject> objectsToRender = new ArrayList<GLObject>();
}
