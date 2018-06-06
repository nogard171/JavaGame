package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Utils.ErrorHandler;

public class GLObject {
	private HashMap<String, GLComponent> components = new HashMap<String, GLComponent>();
	private HashMap<String, GLProperty> properties = new HashMap<String, GLProperty>();
	private String Name = "";
	private GLObjectType type = GLObjectType.BLANK;
	
	public int display = -1;

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

	public ArrayList<GLComponent> getComponents() {
		return new ArrayList<GLComponent>(this.components.values());
	}
	
	public void write(String value)
	{
		System.out.println(value);
	}
	public ArrayList<GLProperty> getProperties() {
		return new ArrayList<GLProperty>(this.properties.values());
	}
	public GLComponent getComponent(String name) {
		GLComponent com = this.components.get(name);
		/*
		 * if(com==null) { new ErrorHandler().LogError("Component: "+
		 * name+", not avaiable under the GLObject: " + this.getName()); }
		 */
		return com;
	}
	public void Destroy()
	{
		Iterator it = this.components.entrySet().iterator();
	    while (it.hasNext()) {
	    	 Map.Entry pair = (Map.Entry)it.next();
	    	 GLComponent com = (GLComponent) pair.getValue();
	         com.Destroy();
	         it.remove(); // avoids a ConcurrentModificationException
	    }
	}

	public GLObjectType getType() {
		return type;
	}

	public void setType(GLObjectType type) {
		this.type = type;
	}
}
