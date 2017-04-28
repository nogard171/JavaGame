
import java.awt.Polygon;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;

public class GLRawQuad
{
	public Vector2f[] vectors = {};
	public byte[] indices = {};
	public Color[] indiceColor= {};
	public Vector2f[] vectorTextures = {};
	public byte[] bones = {};
	public String textureLocation = "";
	public Polygon bounds = null;
}
