package data;

import java.util.HashMap;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import classes.Chunk;
import classes.RawFontCharacter;
import classes.UIControl;

public class UIData {

	public static HashMap<String, RawFontCharacter> fontCharacters = new HashMap<String, RawFontCharacter>();
	public static HashMap<String, UIControl> controls = new HashMap<String, UIControl>();
	public static String selectedMaterial = "";
}
