package classes;

import java.awt.Polygon;

import data.AssetData;

public class Object {
	private int x = 0;
	private int y = 0;
	private Index index;
	private String model = "TILE";
	private String material = "GRASS";
	private String previousMaterial = "GRASS";
	public Polygon bounds;

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		RawMaterial mat = AssetData.materialData.get(material);
		if (mat != null) {
			this.previousMaterial = this.material;
			this.material = material;
		}
	}

	public int getX() {
		return x;
	}

	public void setPosition(int nx, int ny) {
		this.x = nx;
		this.y = ny;
	}

	public int getY() {
		return y;
	}

	public void setIndex(int nx, int nz) {
		index = new Index(nx, nz);
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPreviousMaterial() {
		return previousMaterial;
	}

	public void setPreviousMaterial(String previousMaterial) {
		this.previousMaterial = previousMaterial;
	}

	public Index getIndex() {
		return this.index;
	}

	@Override
	public String toString() {
		return this.index + "(" + this.material + "=>" + this.model + ")";
	}
}
