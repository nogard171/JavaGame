package network;

import java.io.Serializable;

public class GLData implements Serializable{
	
	private static final long serialVersionUID = 3160536359934428752L;
	
	public GLProtocol protocol = GLProtocol.NONE;
	public int ClientID = -1;
}
