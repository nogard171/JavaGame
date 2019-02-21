package core;

public class GLInputAxis {
	private String name = "";
	private String key = "";
	private String alternativeKey = "";

	public GLInputAxis(String newName, String newKey) {
		this.name = newName;
		this.key = newKey;
	}

	public GLInputAxis(String newName, String newKey, String newAlternativeKey) {
		this.name = newName;
		this.key = newKey;
		this.alternativeKey = newAlternativeKey;
	}

	public String getName() {
		return this.name;
	}

	public String getKey() {
		return this.key;
	}

	public String getAlternativeKey() {
		return this.alternativeKey;
	}
}
