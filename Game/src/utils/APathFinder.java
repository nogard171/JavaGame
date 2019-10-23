package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import data.WorldData;

public class APathFinder {
	static HashMap<Point, Point> parentList = new HashMap<Point, Point>();
	static ArrayList<Point> indexes = new ArrayList<Point>();

	public static List constructPath(Point index) {
		LinkedList path = new LinkedList();
		while (parentList.get(index) != null) {
			path.addFirst(index);
			index = parentList.get(index);
		}
		parentList.clear();
		return path;
	}

	public static Point getLowestFValue(HashMap<Point, Integer> fList) {
		Point lowestFValue = null;
		int currentLowest = 1000;
		for (Point index : fList.keySet()) {
			if (fList.get(index) < currentLowest) {
				currentLowest = fList.get(index);
				lowestFValue = index;
			}
		}
		return lowestFValue;
	}

	public static List find(Point startIndex, Point endIndex) {

		if (indexes.size() == 0) {

			indexes.add(new Point(-1, 0));
			indexes.add(new Point(1, 0));
			indexes.add(new Point(0, -1));
			indexes.add(new Point(0, 1));
		}

		LinkedList openList = new LinkedList();
		LinkedList closedList = new LinkedList();
		openList.add(startIndex);
		parentList.put(startIndex, null);

		while (!openList.isEmpty()) {
			Point current = (Point) openList.removeFirst();
			if (current.equals(endIndex)) {
				return constructPath(endIndex);
			} else {
				closedList.add(current);
			}
			if (current.x > WorldData.globalMapData.length && current.y > WorldData.globalMapData[0].length) {
				return null;
			}
			for (Point index : indexes) {
				Point neighborIndex = new Point(current.x + index.x, current.y + index.y);
				if (neighborIndex.x >= 0 && neighborIndex.y >= 0) {
					int data = WorldData.globalMapData[neighborIndex.x][neighborIndex.y];
					if (data > -10) {
						if (!closedList.contains(neighborIndex) && !openList.contains(neighborIndex) && data >= 0) {
							parentList.put(neighborIndex, current);
							openList.add(neighborIndex);
						}
					}
				}
			}
		}
		return null;
	}
}
