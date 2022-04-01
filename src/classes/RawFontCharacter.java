package classes;

public class RawFontCharacter {

	private int displayList = -1;
	private Byte[] blocks;

	public int getDisplayList() {
		return displayList;
	}

	public void setDisplayList(int newDisplayList) {
		this.displayList = newDisplayList;
	}

	public Byte[] getBlocks() {
		return blocks;
	}

	public void setBlocks(Byte[] blocks) {
		this.blocks = blocks;
	}
}
