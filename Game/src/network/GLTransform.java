package network;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector2f;

public class GLTransform extends GLData implements Serializable {

	private static final long serialVersionUID = 8076870183498557214L;
	public Vector2f position = new Vector2f(0,0);
	public float rotation = 0;

}
