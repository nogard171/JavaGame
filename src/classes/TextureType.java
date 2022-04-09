package classes;

public enum TextureType {
	// terrain textures
	AIR, GRASS, DIRT,

	// item textures
	TEST_ITEM;

	public static TextureType getType(String name) {
		TextureType type = AIR;
		switch (name) {
		case "GRASS":
			type = GRASS;
			break;
		case "DIRT":
			type = DIRT;
			break;
		case "AIR":
			type = AIR;
			break;
		}
		type = TextureType.valueOf(name);
		return type;
	}

	public static boolean isMask(TextureType type) {
		boolean mask = false;
		switch (type) {
		case AIR:
			mask = true;
			break;
		}
		return mask;
	}
}
