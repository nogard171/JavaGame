package game;

import org.newdawn.slick.opengl.Texture;

public class Material extends Asset {
	private String materialFilename;
	private int materialTextureID;
	private Size size;

	public Material(String newMaterialFilename) {
		this.materialFilename = newMaterialFilename;
	}

	public String getMaterialFilename() {
		return this.materialFilename;
	}

	public void setTexture(int newMaterialTextureID) {
		this.materialTextureID = newMaterialTextureID;
	}

	public int getTexture() {
		return this.materialTextureID;
	}

	public void setSize(int newWidth, int newHeight) {
		if (this.size == null) {
			this.size = new Size(newWidth, newHeight);
		} else {
			this.size.setWidth(newWidth);
			this.size.setHeight(newHeight);
		}
	}
}
