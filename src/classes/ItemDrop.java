package classes;

public class ItemDrop {
	public String item;
	public int minDrop = 1;
	public int maxDrop = 1;

	public ItemDrop(String itemData) {
		this(itemData, 1, 1);
	}

	public ItemDrop(String itemData, int min, int max) {
		this.item = itemData;
		this.minDrop = min;
		this.maxDrop = max;
	}
}
