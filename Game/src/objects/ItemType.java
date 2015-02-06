package objects;

import java.awt.image.BufferedImage;

import util.ImageLoader;

public enum ItemType {
	Blank,
	Log, 
	Maple_Log;
	int size=32;
	BufferedImage itemSet;
	ItemType()
	{
		itemSet = ImageLoader.getImageFromResources("\\resources\\image\\itemset.png");
	}
	public BufferedImage getResourceTexture(ItemType type)
	    {
			switch(type)
			{
			    case Blank:
			    	return itemSet.getSubimage(0, 0, size, size);
			    case Log:
			    	return itemSet.getSubimage(0, size*1, size, size);
			    default:
			    	return itemSet.getSubimage(0, size*2, size, size);
			}
	    }
}
