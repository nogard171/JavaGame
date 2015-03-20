package objects;

import java.awt.image.BufferedImage;

import util.ImageLoader;

public enum ItemType {
	Blank, Log, Maple_Log, Soul_Log, Rock;
	static int size = 32;
	static BufferedImage itemSet;

	ItemType() {

	}

	public static BufferedImage getResourceTexture(ItemType type) {
		itemSet = ImageLoader
				.getImageFromResources("\\resources\\image\\itemset.png");
		switch (type) {
		case Blank:
			return itemSet.getSubimage(0, 0, size, size);
		case Log:
			return itemSet.getSubimage(0, size * 1, size, size);
		case Maple_Log:
			return itemSet.getSubimage(32, size * 1, size, size);
		case Soul_Log:
			return itemSet.getSubimage(64, size * 1, size, size);
		case Rock:
			return itemSet.getSubimage(0, size * 2, size, size);
		default:
			return itemSet.getSubimage(0, 0, size, size);
		}
	}
}
