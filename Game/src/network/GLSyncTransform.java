package network;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector2f;

public class GLSyncTransform extends GLData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9109339155226911921L;
	public Vector2f position = new Vector2f(-1,-1);
	public GLSyncTransform()
	{
		this.protocol = GLProtocol.TRANSFORM;
	}
}
