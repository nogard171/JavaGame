package components;

public class GLComponent {

	private String type = "";

	public GLComponent() {
		this.type = this.getClass().getName().replace("classes.", "");
	}

	public void SetType(String newType) {
		this.type = newType;
	}

	public String GetType() {
		return this.type;
	}

	public void Run() {

	}

}
