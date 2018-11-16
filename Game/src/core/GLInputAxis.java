package core;

public class GLInputAxis {
	private String name = "";
	private String key = "";

	public GLInputAxis(String newName, String newKey) {
		this.name = newName;
		this.key = newKey;
	}

	public String getName() {
		return this.name;
	}

	public String getKey() {
		return this.key;
	}
}
