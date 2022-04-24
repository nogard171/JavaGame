package classes;

import java.util.HashMap;

public class ItemData {
	public TextureType type;
	public HashMap<String, String> attributes = new HashMap<String, String>();

	public ItemData(TextureType newType) {
		this.type = newType;
	}

	public ItemData() {
	}
}
