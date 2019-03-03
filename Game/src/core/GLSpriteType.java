package core;

public enum GLSpriteType {
	NONE, GRASS;
	public static GLSpriteType getType(String newType) {
		GLSpriteType type = GLSpriteType.NONE;
		switch (newType.toLowerCase()) {
		case "grass":
			type = GLSpriteType.GRASS;
			break;
		case "none":
			type = GLSpriteType.GRASS;
			break;
		}
		return type;
	}
}
