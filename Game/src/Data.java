

import java.io.Serializable;

public class Data implements Serializable {
	public Data(String newCommand, String newMessage) {
		this.command = newCommand;
		this.message = newMessage;
	}

	public Data(String newCommand) {
		this.command = newCommand;
	}

	public String command = "";
	public String message = "";
	public Account account = null;
}
