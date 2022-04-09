package data;

import java.util.HashMap;
import org.newdawn.slick.opengl.Texture;

import classes.ItemData;
import classes.RawMaterial;
import classes.RawModel;
import classes.ResourceData;

public class AssetData {
//Material Data
	public static Texture texture;
	public static HashMap<String, RawMaterial> materialData = new HashMap<String, RawMaterial>();

	// Model Data
	public static HashMap<String, RawModel> modelData = new HashMap<String, RawModel>();
	
	//resource Data
	public static HashMap<String, ResourceData> resourceData = new HashMap<String, ResourceData>();
	public static HashMap<String, ItemData> itemData = new HashMap<String, ItemData>();
}
