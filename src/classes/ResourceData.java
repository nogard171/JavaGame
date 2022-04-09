package classes;

import java.util.ArrayList;

public class ResourceData {
	public int hitpoints = 10;
	public ArrayList<ItemDrop> itemDrops = new ArrayList<ItemDrop>();

	public ResourceData() {

	}

	public ResourceData(int newHitPoints, ArrayList<ItemDrop> newItemDrops) {
		this.hitpoints = newHitPoints;
		this.itemDrops = newItemDrops;
	}
}
