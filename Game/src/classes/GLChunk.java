package classes;

import java.awt.Point;
import java.util.ArrayList;

public class GLChunk {
	public ArrayList<GLObject> objects = new ArrayList<GLObject>();
	GLSize chunkSize;

	public int chunkDisplayList = -1;

	public GLChunk() {
		chunkSize = new GLSize(10, 10, 1);
		for (int x = 0; x < chunkSize.width; x++) {
			for (int y = 0; y < chunkSize.height; y++) {
				float newX = (x * 32);
				float newY = (y * 32);

				GLObject obj = new GLObject();
				obj.position = new GLPosition(newX, newY);
				obj.type = "GRASS";
				objects.add(obj);
			}
		}
	}
}
