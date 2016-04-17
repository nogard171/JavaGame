

import java.io.Serializable;

public class Account  implements Serializable{
	public String username = "";
	public String password = "";

	public Account(String newUsername, String newPassword) {
		this.username = newUsername;
		this.password = newPassword;
	}

	public Account() {

	}
}
