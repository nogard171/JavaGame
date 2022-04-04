package threads;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

import classes.Chunk;
import classes.Index;
import data.EngineData;
import data.Settings;
import utils.APathFinder;
import classes.Object;
import classes.Path;

public class BackEndThread extends BaseThread {

	public static LinkedList<Path> completedPaths = new LinkedList<Path>();
	public static LinkedList<Path> paths = new LinkedList<Path>();

	public static void findPath(Path inputPath) {
		boolean hasPath = false;
		for (Path tempPath : completedPaths) {
			if (tempPath.start.equals(inputPath.start) && tempPath.end.equals(inputPath.end)) {
				hasPath = true;
				break;
			}
		}
		if (!hasPath) {
			paths.add(inputPath);
		}
	}

	public static Path getPath(Path inputPath) {
		Path path = inputPath;
		Path removedPath = null;
		int i = 0;
		for (Path tempPath : completedPaths) {
			if (tempPath.start.equals(inputPath.start) && tempPath.end.equals(inputPath.end)) {
				removedPath = tempPath;
				break;
			}
			i++;
		}
		if (completedPaths.size() > 0) {
			boolean removed = completedPaths.remove(removedPath);
			if (removed) {
				if (removedPath != null) {
					path = removedPath;
				}
			}
		}
		return path;
	}

	boolean isRunning = true;

	@Override
	public void run() {
		setup();
		while (isRunning) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			update();
		}
		clean();
	}

	@Override
	public void setup() {
		super.setup();
	}

	@Override
	public void update() {
		super.update();
		if (paths.size() > 0) {
			Path path = paths.removeFirst();
			System.out.println("Looking for Path");
			path.steps = APathFinder.find(path.start, path.end);
			System.out.println("Found a Path");
			completedPaths.add(path);
		}

		if (Settings.localChunks) {
			if (EngineData.cleanupChunks.size() > 0) {
				Chunk chunk = EngineData.cleanupChunks.removeFirst();
				if (chunk != null) {
					save(chunk);
					remove(chunk);
				}
			}
			if (EngineData.loadChunks.size() > 0) {
				Index index = EngineData.loadChunks.removeFirst();
				if (index != null) {
					if (index.getX() >= 0 && index.getY() >= 0) {
						load(index);
					}
				}
			}
		}
	}

	@Override
	public void clean() {
		super.clean();
	}

	public void load(Index index) {
		String fileName = EngineData.worldFolder + "chunks/chunk" + index.getX() + "," + index.getY() + ".chunk";
		if (Files.exists(java.nio.file.Path.of(fileName))) {
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				String line;
				Chunk newChunk = new Chunk(index.getX(), index.getY());
				newChunk.setupAsBlank();
				while ((line = br.readLine()) != null) {
					String[] data = line.split(" ");
					int x = Integer.parseInt(data[1]);
					int y = Integer.parseInt(data[2]);
					int chunkX = (index.getX() * EngineData.chunkSize.getWidth());
					int chunkY = (index.getY() * EngineData.chunkSize.getDepth());
					String material = data[3];
					String model = data[4];

					Object obj = new Object();
					obj.setMaterial(material);
					obj.setModel(model);

					int carX = x * 32;
					int carY = y * 32;
					int isoX = carX - carY;
					int isoY = (carY + carX) / 2;

					obj.setIndex(x + chunkX, y + chunkY);
					obj.setPosition(isoX, isoY);

					if (line.startsWith("g")) {
						newChunk.ground[x][y] = obj;
					}
					if (line.startsWith("o")) {
						newChunk.objects[x][y] = obj;
					}
				}
				newChunk.refresh();
				EngineData.chunks.put(index.getString(), newChunk);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void save(Chunk chunk) {
		String fileName = EngineData.worldFolder + "chunks/chunk" + chunk.getIndex().getX() + ","
				+ chunk.getIndex().getY() + ".chunk";

		try {
			FileWriter myWriter = new FileWriter(fileName);
			for (int x = 0; x < EngineData.chunkSize.getWidth(); x++) {
				for (int z = 0; z < EngineData.chunkSize.getDepth(); z++) {
					Object ground = chunk.ground[x][z];
					if (ground != null) {
						myWriter.write(
								"g " + x + " " + z + " " + ground.getMaterial() + " " + ground.getModel() + '\n');
					}
					Object obj = chunk.objects[x][z];
					if (obj != null) {
						myWriter.write("o " + x + " " + z + " " + obj.getMaterial() + " " + obj.getModel() + '\n');
					}
				}
			}
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void remove(Chunk chunk) {
		EngineData.chunks.remove(chunk.getIndex().toString());
	}
}
