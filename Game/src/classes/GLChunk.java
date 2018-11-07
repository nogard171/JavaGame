package classes;

import java.util.ArrayList;

import utils.GLHandler;

public class GLChunk {
	private int dl = -1;
	private GLModelData[][][] models;
	private ArrayList<GLModelData> modelsToRender = new ArrayList<GLModelData>();
	private GLPosition position = new GLPosition(0, 0);

	public GLChunk() {
		this.SetupChunk();
	}

	public GLChunk(int x, int y) {
		this.position = new GLPosition((x - y) * 32 * 16, ((y + x) * 16) * 16);
		this.SetupChunk();
	}

	private void SetupChunk() {
		models = new GLModelData[16][16][1];
		int xCount = models.length;
		int yCount = models[0].length;
		int zCount = models[0][0].length;

		for (int x = 0; x < xCount; x++) {
			for (int y = 0; y < yCount; y++) {
				for (int z = 0; z < zCount; z++) {
					GLModelData model = new GLModelData();
					model.setName("grass");
					model.setQuadName("grass_top");
					model.setStart(new GLPosition(0.5f, 0));
					model.setSize(new GLSize(0.5f, 0.5f));
					model.setPosition(new GLPosition((x - y) * 32, ((y + x) * 16) + (z * 32)));
					models[x][y][z] = model;
				}
			}
		}
	}

	public ArrayList<GLModelData> GetModelsToRender() {
		return modelsToRender;
	}

	public GLModelData[][][] GetModels() {
		return this.models;
	}

	public void ClearModelsToRender() {
		modelsToRender.clear();
	}

	public void AddModel(GLModelData modelData) {
		modelsToRender.add(modelData);
	}

	public int GetDisplayList() {
		return this.dl;
	}

	public void SetDisplayList(int newDL) {
		this.dl = newDL;
	}

	public GLPosition getPosition() {
		return this.position;
	}
}
