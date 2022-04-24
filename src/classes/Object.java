package classes;

import java.awt.Polygon;

import data.AssetData;
import data.EngineData;
import utils.Generator;

public class Object {
	private int x = 0;
	private int y = 0;
	private Index index;
	private String model = "TILE";
	private String material = "GRASS";
	public Polygon bounds;
	private String hash = "";

	public Object() {
		String temp = Generator.getRandom(32);
		while (EngineData.hashes.contains(temp)) {
			temp = Generator.getRandom(32);
		}
		this.hash = temp;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
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

	public Index getIndex() {
		return this.index;
	}

	@Override
	public String toString() {
		return this.index + "(" + this.material + "=>" + this.model + ")";
	}

	public String getHash() {
		return hash;
	}
}
