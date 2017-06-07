package engine;

public class GLComponent {
	private String Name = "";
	private GLObject object;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public GLObject getObject() {
		return object;
	}

	public void setObject(GLObject object) {
		this.object = object;
	}
}
