package Engine;

import java.util.ArrayList;
import java.util.HashMap;

public class GLObject {
	private HashMap<String,GLComponent> components = new HashMap<String,GLComponent>();
	private String Name = "";

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public void AddComponent(GLComponent com) {
		com.setObject(this);
		this.components.put(com.getName(),com);
	}

	public void RemoveComponent(GLComponent com) {
		this.components.remove(com.getName());
	}

	public GLComponent getComponent(String name) {
		return components.get(name);
	}
}
