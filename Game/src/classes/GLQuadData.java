package classes;

public class GLQuadData {
	private GLPosition[] vectors;
	private byte[] indices;
	private String name;

	public GLQuadData(String string) {
		this.setName(string);
	}

	public void SetVectors(GLPosition[] newVectors) {
		this.vectors = newVectors;
	}

	public void SetIndices(byte[] newIndices) {
		this.indices = newIndices;
	}

	public GLPosition GetVectors(byte indice) {
		return this.vectors[indice];
	}

	public byte[] GetIndices() {
		return this.indices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
