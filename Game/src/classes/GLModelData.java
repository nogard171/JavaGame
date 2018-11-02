package classes;

public class GLModelData {
	private String name = "";
	private String quadName = "";
	private String orientation = "";
	private GLPosition start = new GLPosition();
	private GLSize size = new GLSize();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuadName() {
		return quadName;
	}

	public void setQuadName(String quadName) {
		this.quadName = quadName;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public GLPosition getStart() {
		return start;
	}

	public void setStart(GLPosition start) {
		this.start = start;
	}

	public GLSize getSize() {
		return size;
	}

	public void setSize(GLSize size) {
		this.size = size;
	}
}
