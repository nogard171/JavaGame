package Engine;

import java.util.ArrayList;
import java.util.HashMap;

public class GLObject {
	private HashMap<String, GLComponent> components = new HashMap<String, GLComponent>();
	private HashMap<String, GLProperty> properties = new HashMap<String, GLProperty>();
	private String Name = "";
	private int displayHandleID = -1;

	public int getDisplayHandleID() {
		return displayHandleID;
	}

	public void setDisplayHandleID(int displayHandleID) {
		this.displayHandleID = displayHandleID;
	}

	public String getName() {
		return this.Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public void AddProperty(GLProperty prop) {
		this.properties.put(prop.getName(), prop);
	}

	public void RemoveProperty(GLProperty prop) {
		this.properties.remove(prop);
	}

	public GLProperty getProperty(String name) {
		return this.properties.get(name);
	}
	public void AddComponent(GLComponent com) {
		com.setObject(this);
		this.components.put(com.getName(), com);
	}

	public void RemoveComponent(GLComponent com) {
		this.components.remove(com.getName());
	}

	public GLComponent getComponent(String name) {
		return this.components.get(name);
	}
}
