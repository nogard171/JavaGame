package core;

import org.lwjgl.opengl.GL11;

import game.DataHub;
import game.Game;
import utils.GLRenderer;

public class GLChunk {
	private int displayListID = -1;
	GLObjectStack[][] objectStacks;
	GLSize stackSize = new GLSize(16, 16);

	public GLChunk() {
	}

	public GLChunk(int newStackWidth, int newStackHeight) {
		this.stackSize = new GLSize(newStackWidth, newStackHeight);
	}

	public void create() {
		objectStacks = new GLObjectStack[this.stackSize.getWidth()][this.stackSize.getHeight()];
		for (int stackX = 0; stackX < stackSize.getWidth(); stackX++) {
			for (int stackY = 0; stackY < stackSize.getHeight(); stackY++) {
				objectStacks[stackX][stackY] = new GLObjectStack();
			}
		}

	}

	public void generate() {
		for (int stackX = 0; stackX < stackSize.getWidth(); stackX++) {
			for (int stackY = 0; stackY < stackSize.getHeight(); stackY++) {
				GLObject obj = new GLObject();
				obj.setSpriteType(GLSpriteType.GRASS);
				if (stackX == 0 && stackY == 0) {
					GLObject obj2 = new GLObject();
					obj2.setSpriteType(GLSpriteType.TREE_TOP);
					obj2.setPosition(stackX * 32, stackY * 32);
					objectStacks[stackX][stackY].addObject(obj2, 2);

					GLObject obj3 = new GLObject();
					obj3.setSpriteType(GLSpriteType.TREE_TRUNK);
					obj3.setPosition(stackX * 32, stackY * 32);
					objectStacks[stackX][stackY].addObject(obj3, 1);
				}
				obj.setPosition(stackX * 32, stackY * 32);
				objectStacks[stackX][stackY].addObject(obj, 0);
			}
		}
	}

	public void build() {
		this.displayListID = GL11.glGenLists(1);
		GL11.glNewList(this.displayListID, GL11.GL_COMPILE);
		for (int stackX = 0; stackX < stackSize.getWidth(); stackX++) {
			for (int stackY = 0; stackY < stackSize.getHeight(); stackY++) {
				GLObjectStack objStack = objectStacks[stackX][stackY];
				int layerMax = objStack.getLayerMax();
				System.out.println("max: " + layerMax);
				for (int stackMax = 0; stackMax < layerMax; stackMax++) {
					GLObject obj = objStack.getObjectAtIndex(stackMax);
					if (obj != null) {
						GLRenderer.RenderSprite(obj.getPosition(), DataHub.spriteData.get(obj.getSpriteType()));
					}
				}
			}
		}
		GL11.glEndList();
	}

	public void render() {
		if (this.displayListID != -1) {
			GL11.glCallList(this.displayListID);
		}
	}
}
