package core;

public enum GLSpriteType {
	NONE, GRASS,DIRT,WATER,STONE;
	public static GLSpriteType getType(String newType) {
		GLSpriteType type = GLSpriteType.NONE;
		switch (newType.toLowerCase()) {
		case "grass":
			type = GLSpriteType.GRASS;
			break;
		case "none":
			type = GLSpriteType.GRASS;
			break;
		case "dirt":
			type = GLSpriteType.DIRT;
			break;
		case "water":
			type = GLSpriteType.WATER;
			break;
		case "stone":
			type = GLSpriteType.STONE;
			break;
		}
		return type;
	}
}
