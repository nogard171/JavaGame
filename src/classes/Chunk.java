package classes;

import java.awt.Point;
import java.awt.Polygon;
import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import data.AssetData;
import data.EngineData;
import utils.Input;
import utils.Renderer;

public class Chunk {
	private Color boundsColor = Color.red;
	public Index index;
	public int displayListID = -1;
	private boolean needsUpdating = true;
	public Polygon bounds;

	public Object[][] ground;
	public Object[][] objects;
	public Object[][] characters;
	public boolean[][] passable;

	public Chunk(int i, int j) {
		index = new Index(i, j);
	}

	public Index getIndex() {
		return index;
	}

	private int maxBoundHeight = 512;

	public void setup() {
		Random r = new Random();
		bounds = new Polygon();
		int carX0 = (index.getX() * 32) * 16;
		int carY0 = (index.getY() * 32) * 16;
		int isoX0 = carX0 - carY0;
		int isoY0 = (carY0 + carX0) / 2;

		bounds.addPoint(isoX0, isoY0 - maxBoundHeight);
		bounds.addPoint(isoX0 + (EngineData.chunkSize.getWidth() * 32),
				isoY0 + (EngineData.chunkSize.getDepth() * 16) - maxBoundHeight);
		bounds.addPoint(isoX0 + (EngineData.chunkSize.getWidth() * 32), isoY0 + (EngineData.chunkSize.getDepth() * 16));
		bounds.addPoint(isoX0, isoY0 + (EngineData.chunkSize.getDepth() * 32));
		bounds.addPoint(isoX0 - (EngineData.chunkSize.getWidth() * 32), isoY0 + (EngineData.chunkSize.getDepth() * 16));
		bounds.addPoint(isoX0 - (EngineData.chunkSize.getWidth() * 32),
				isoY0 + (EngineData.chunkSize.getDepth() * 16) - maxBoundHeight);

		ground = new Object[EngineData.chunkSize.getWidth()][EngineData.chunkSize.getDepth()];
		objects = new Object[EngineData.chunkSize.getWidth()][EngineData.chunkSize.getDepth()];
		characters = new Object[EngineData.chunkSize.getWidth()][EngineData.chunkSize.getDepth()];
		passable = new boolean[EngineData.chunkSize.getWidth()][EngineData.chunkSize.getDepth()];
		for (int x = 0; x < EngineData.chunkSize.getWidth(); x++) {
			for (int z = 0; z < EngineData.chunkSize.getDepth(); z++) {
				int carX = x * 32;
				int carY = z * 32;
				int isoX = carX - carY;
				int isoY = (carY + carX) / 2;
				Object obj = new Object();
				obj.setIndex(x + (index.getX() * EngineData.chunkSize.getWidth()),
						z + (index.getY() * EngineData.chunkSize.getDepth()));
				obj.setPosition(isoX, isoY);
				obj.setMaterial("GRASS0");
				if (r.nextFloat() < 0.1f) {
					obj.setMaterial("GRASS1");
				}
				if (r.nextFloat() < 0.1f) {
					obj.setMaterial("DIRT");
				}
				if (r.nextFloat() < 0.1f) {
					obj.setMaterial("SAND");
				}
				if (r.nextFloat() < 0.1f) {
					obj.setMaterial("STONE");
				}
				ground[x][z] = obj;
				passable[x][z] = true;
				if (x == 5 && z == 5 || r.nextFloat() < 0.05f) {
					obj = new Object();
					obj.setIndex(x + (index.getX() * EngineData.chunkSize.getWidth()),
							z + (index.getY() * EngineData.chunkSize.getDepth()));
					obj.setPosition(isoX, isoY);
					obj.setModel("TREE");
					obj.setMaterial("TREE");
					objects[x][z] = obj;
					passable[x][z] = false;
				}

			}
		}
	}

	public void setupAsBlank() {
		// create an empty bounds for the chunk
		bounds = new Polygon();
		// convert the index to a x and y for the map
		int carX0 = (index.getX() * 32) * 16;
		int carY0 = (index.getY() * 32) * 16;
		int isoX0 = carX0 - carY0;
		int isoY0 = (carY0 + carX0) / 2;
		// add the folowing points which outline the maps corners.
		bounds.addPoint(isoX0, isoY0 - maxBoundHeight);
		bounds.addPoint(isoX0 + (EngineData.chunkSize.getWidth() * 32),
				isoY0 + (EngineData.chunkSize.getDepth() * 16) - maxBoundHeight);
		bounds.addPoint(isoX0 + (EngineData.chunkSize.getWidth() * 32), isoY0 + (EngineData.chunkSize.getDepth() * 16));
		bounds.addPoint(isoX0, isoY0 + (EngineData.chunkSize.getDepth() * 32));
		bounds.addPoint(isoX0 - (EngineData.chunkSize.getWidth() * 32), isoY0 + (EngineData.chunkSize.getDepth() * 16));
		bounds.addPoint(isoX0 - (EngineData.chunkSize.getWidth() * 32),
				isoY0 + (EngineData.chunkSize.getDepth() * 16) - maxBoundHeight);

		// clear all data including ground, objects and characters.
		ground = new Object[EngineData.chunkSize.getWidth()][EngineData.chunkSize.getDepth()];
		objects = new Object[EngineData.chunkSize.getWidth()][EngineData.chunkSize.getDepth()];
		characters = new Object[EngineData.chunkSize.getWidth()][EngineData.chunkSize.getDepth()];
		for (int x = 0; x < EngineData.chunkSize.getWidth(); x++) {
			for (int z = 0; z < EngineData.chunkSize.getDepth(); z++) {
				ground[x][z] = null;
				objects[x][z] = null;
				characters[x][z] = null;
				passable[x][z] = true;
			}
		}
	}

	private void build() {
		displayListID = GL11.glGenLists(1);
		GL11.glNewList(displayListID, GL11.GL_COMPILE);
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int x = 0; x < EngineData.chunkSize.getWidth(); x++) {
			for (int z = 0; z < EngineData.chunkSize.getDepth(); z++) {
				if (EngineData.path != null) {
					if (EngineData.path.contains(new Point(x, z))) {
						GL11.glColor4f(1, 0, 0, 0.5f);
					} else {
						GL11.glColor4f(1, 1, 1, 1f);
					}
				} else {
					GL11.glColor4f(1, 1, 1, 1f);
				}
				// Renderer.renderModel(this, x, z);
				int carX = (index.getX() * 32) * 16;
				int carY = (index.getY() * 32) * 16;
				int isoX = carX - carY;
				int isoY = (carY + carX) / 2;
				int selfX = isoX;
				int selfY = isoY;
				Object obj = ground[x][z];
				if (obj != null) {
					RawModel raw = AssetData.modelData.get(obj.getModel());
					if (raw != null) {
						if (obj.bounds == null) {
							obj.bounds = new Polygon();
							for (Vector2f vec : raw.boundVectors) {
								obj.bounds.addPoint((int) (vec.x + selfX + obj.getX()),
										(int) (vec.y + selfY + obj.getY()));

							}
						}
						RawMaterial mat = AssetData.materialData.get(obj.getMaterial());
						if (mat != null) {
							int tic = 0;
							for (byte i : raw.indices) {
								Vector2f textureVec = mat.vectors[i];
								if (mat.indices.length > 0) {
									byte ti = mat.indices[tic];
									textureVec = mat.vectors[ti];
								}
								GL11.glTexCoord2f(textureVec.x / AssetData.texture.getImageWidth(),
										textureVec.y / AssetData.texture.getImageHeight());
								Vector2f vec = raw.vectors[i];
								GL11.glVertex2f(vec.x + selfX + obj.getX(), vec.y + selfY + obj.getY());
								tic++;
							}
						}
					}
				}
				obj = objects[x][z];
				if (obj != null) {
					RawModel raw = AssetData.modelData.get(obj.getModel());
					if (raw != null) {
						if (obj.bounds == null) {
							obj.bounds = new Polygon();
							for (Vector2f vec : raw.boundVectors) {
								obj.bounds.addPoint((int) (vec.x + selfX + obj.getX()),
										(int) (vec.y + selfY + obj.getY()));
							}
						}
						RawMaterial mat = AssetData.materialData.get(obj.getMaterial());
						if (mat != null) {
							int tic = 0;
							for (byte i : raw.indices) {
								Vector2f textureVec = mat.vectors[i];
								if (mat.indices.length > 0) {
									byte ti = mat.indices[tic];
									textureVec = mat.vectors[ti];
								}
								GL11.glTexCoord2f(textureVec.x / AssetData.texture.getImageWidth(),
										textureVec.y / AssetData.texture.getImageHeight());
								Vector2f vec = raw.vectors[i];
								GL11.glVertex2f(vec.x + selfX + obj.getX(), vec.y + selfY + obj.getY());
								tic++;
							}
						}
					}
				}
				obj = characters[x][z];
				if (obj != null) {
					RawModel raw = AssetData.modelData.get(obj.getModel());
					if (raw != null) {
						if (obj.bounds == null) {
							obj.bounds = new Polygon();
							for (Vector2f vec : raw.boundVectors) {
								obj.bounds.addPoint((int) (vec.x + selfX + obj.getX()),
										(int) (vec.y + selfY + obj.getY()));
							}
						}
						RawMaterial mat = AssetData.materialData.get(obj.getMaterial());
						if (mat != null) {
							int tic = 0;
							for (byte i : raw.indices) {
								Vector2f textureVec = mat.vectors[i];
								if (mat.indices.length > 0) {
									byte ti = mat.indices[tic];
									textureVec = mat.vectors[ti];
								}
								GL11.glTexCoord2f(textureVec.x / AssetData.texture.getImageWidth(),
										textureVec.y / AssetData.texture.getImageHeight());
								Vector2f vec = raw.vectors[i];
								GL11.glVertex2f(vec.x + selfX + obj.getX(), vec.y + selfY + obj.getY());
								tic++;
							}
						}
					}
				}
			}
		}
		GL11.glEnd();
		GL11.glEndList();
	}

	public void update() {
		if (needsUpdating) {
			this.build();
			needsUpdating = false;
		}
	}

	public LinkedList<Object> getHoveredObjects() {
		LinkedList<Object> temp = new LinkedList<Object>();

		int cartX = (int) ((int) Input.getMousePosition().getX() - View.getX());
		int cartY = (int) ((int) Input.getMousePosition().getY() - View.getY());
		for (int x = 0; x < EngineData.chunkSize.getWidth(); x++) {
			for (int z = 0; z < EngineData.chunkSize.getDepth(); z++) {
				Object obj = ground[x][z];
				if (obj != null) {
					if (obj.bounds.contains(new Point(cartX, cartY))) {
						temp.add(obj);
					}
				}
				obj = objects[x][z];
				if (obj != null) {
					if (obj.bounds != null) {
						if (obj.bounds.contains(new Point(cartX, cartY))) {
							temp.add(obj);
						}
					}
				}
				obj = characters[x][z];
				if (obj != null) {
					if (obj.bounds != null) {
						if (obj.bounds.contains(new Point(cartX, cartY))) {
							temp.add(obj);
						}
					}
				}
			}
		}
		return temp;
	}

	public void render() {
		if (displayListID == -1) {
			this.build();
		}
		if (displayListID != -1) {
			GL11.glCallList(displayListID);
		}
		if (EngineData.showTelematry) {
			if (this.bounds != null) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
				GL11.glColor4f(this.boundsColor.getRed(), this.boundsColor.getGreen(), this.boundsColor.getBlue(), 1f);

				GL11.glBegin(GL11.GL_POLYGON);
				for (int p = 0; p < this.bounds.npoints; p++) {
					GL11.glVertex2f(this.bounds.xpoints[p], this.bounds.ypoints[p]);
				}
				GL11.glEnd();
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}
		}
	}

	public void destroy() {

	}

	public void refresh() {
		this.needsUpdating = true;
	}

	public void setBoundsColor(Color boundsColor) {
		this.boundsColor = boundsColor;
	}

	public void addCharacter(Index index, Object character) {
		if (characters != null) {
			int x = index.getX();
			int z = index.getY();
			Object charc = characters[x][z];
			if (charc == null) {
				charc = character;
				int carX = x * 32;
				int carY = z * 32;
				int isoX = carX - carY;
				int isoY = (carY + carX) / 2;
				charc.setIndex(x + (this.index.getX() * EngineData.chunkSize.getWidth()),
						z + (this.index.getY() * EngineData.chunkSize.getDepth()));
				charc.setPosition(isoX, isoY);
			}
			characters[x][z] = charc;
		}
	}
}
