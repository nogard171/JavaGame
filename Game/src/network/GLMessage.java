package network;

import java.io.Serializable;

public class GLMessage extends GLData implements Serializable {

	private static final long serialVersionUID = 8076870183498557214L;
	public String from = "";
	public String to = "";
	public String message = "";
}
