package classes;

public class Sprite extends Component {
	private int textureID = -1;
	private SpriteFrame[][] sprites;

	public Sprite(int x, int y) {
		sprites = new SpriteFrame[x][y];
	}

	public SpriteFrame getFrame(int X, int Y) {
		return this.sprites[X][Y];
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public void setFrame(int displayListID, int x, int y) {
		SpriteFrame spriteFrame = new SpriteFrame();

		spriteFrame.setDisplayListID(displayListID);

		sprites[x][y] = spriteFrame;
	}
}
