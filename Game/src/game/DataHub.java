package game;

import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;

import core.GLSpriteData;
import core.GLSpriteType;

public class DataHub {
	public static String spriteSheet = "";
	public static Texture texture = null;
	public static HashMap<GLSpriteType, GLSpriteData> spriteData = new HashMap<GLSpriteType, GLSpriteData>();
}
