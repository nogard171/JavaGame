package classes;

import java.util.ArrayList;
import java.util.HashMap;

public class ResourceData {
	public int hitpoints = 10;
	public HashMap<String, ResourceState> states = new HashMap<String, ResourceState>();

	public ResourceData() {

	}

	public ResourceData(int newHitPoints) {
		this.hitpoints = newHitPoints;
	}
}
