package core;

public enum GLSpriteType {
	NONE, GRASS,DIRT,WATER,STONE,TREE_TRUNK,TREE_TOP;
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
		case "tree_trunk":
			type = GLSpriteType.TREE_TRUNK;
			break;
		case "tree_top":
			type = GLSpriteType.TREE_TOP;
			break;
		}
		return type;
	}
}
