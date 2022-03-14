package data;

import java.util.HashMap;
import org.newdawn.slick.opengl.Texture;
import classes.RawMaterial;
import classes.RawModel;

public class AssetData {
//Material Data
	public static Texture texture;
	public static HashMap<String, RawMaterial> materialData = new HashMap<String, RawMaterial>();

	// Model Data
	public static HashMap<String, RawModel> modelData = new HashMap<String, RawModel>();
}
