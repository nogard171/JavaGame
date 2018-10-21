package classes;

public class GLQuadData {
	private GLPosition[] vectors;
	private byte[] indices;

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
}
