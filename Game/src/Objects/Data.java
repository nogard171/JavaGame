package Objects;

import java.io.Serializable;

public class Data implements Serializable {
	public Data(String newCommand, String newMessage) {
		this.command = newCommand;
		this.message = newMessage;
	}

	public String command = "";
	public String message = "";
}
