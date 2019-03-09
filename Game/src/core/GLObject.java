package core;

import utils.GLGenerator;

public class GLObject {
	private String objectHash = "";
	private GLPosition position = new GLPosition(0, 0);
	private GLPosition spriteOffset = new GLPosition(0, 0);
	private GLSpriteType spriteType = GLSpriteType.NONE;

	public GLObject() {
		this.objectHash = GLGenerator.generateObjectID();
	}

	public void setPosition(float newX, float newY) {
		this.position.setPosition(newX, newY);
	}

	public void setSpriteOffset(float newX, float newY) {
		this.spriteOffset.setPosition(newX, newY);
	}

	public GLPosition getPosition() {
		return this.position;
	}

	public GLPosition getSpriteOffset() {
		return this.spriteOffset;
	}

	public GLSpriteType getSpriteType() {
		return spriteType;
	}

	public void setSpriteType(GLSpriteType newSpriteType) {
		this.spriteType = newSpriteType;
	}
	
	public String getHash()
	{
		return this.objectHash.toLowerCase();
	}
}
