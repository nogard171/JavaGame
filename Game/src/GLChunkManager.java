import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class GLChunkManager {
	public void Update() {
		int mouseX = (int) (Mouse.getX() - new GLDataHub().camera.position.x - 32);
		int mouseY = (int) (new GLDataHub().camera.size.y - (int) (Mouse.getY() + new GLDataHub().camera.position.y));

		boolean cameraChanged = new GLDataHub().camera.changed;
		if (cameraChanged) {
			new GLDataHub().chunkToRender = this.getChunksInCamera();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			//new GLDataHub().chunkToRender.get(0).moveObject(0, 0, "PLAYER");
		}

		for (GLChunk chunk : new GLDataHub().chunkToRender) {
			for (int y = 0; y < chunk.data.length; y++) {
				for (int x = 0; x < chunk.data[y].length; x++) {
					GLObjectStack objectStack = chunk.data[x][y];
					GLObject obj = objectStack.objects.get(0);
					if (new GLMath().polygonContainsPoint(obj.polygon, new Point(mouseX, mouseY))&&Mouse.isButtonDown(0)) {
						chunk.moveObject(chunk,x, y, "PLAYER");
					}
				}
			}
		}
	}

	public ArrayList<GLChunk> getChunksInCamera() {
		GLCamera camera = new GLDataHub().camera;
		ArrayList<GLChunk> newChunks = new ArrayList<GLChunk>();

		for (GLChunk chunk : new GLDataHub().chunks) {
			if (camera.containsChunk(chunk)) {
				newChunks.add(chunk);
			}
		}
		return newChunks;
	}
}
