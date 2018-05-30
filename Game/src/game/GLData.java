package game;

import java.util.HashMap;

import classes.GLModel;
import classes.GLSprite;
import engine.GLShader;

public class GLData {
	public static HashMap<String, GLSprite> sprites = new HashMap<String, GLSprite>();
	public static HashMap<String, GLModel> models = new HashMap<String, GLModel>();
	public static GLShader shader;
}
