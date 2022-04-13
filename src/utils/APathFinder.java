package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import classes.Index;
import classes.World;
import data.EngineData;
import classes.Object;

public class APathFinder {
	static HashMap<Point, Point> parentList = new HashMap<Point, Point>();
	static ArrayList<Point> indexes = new ArrayList<Point>();

	public static LinkedList<Point> constructPath(Point index) {
		LinkedList<Point> path = new LinkedList<Point>();
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

	public static LinkedList<Point> find(Point startIndex, Point endIndex) {

		if (indexes.size() == 0) {

			indexes.add(new Point(-1, 0));
			indexes.add(new Point(1, 0));
			indexes.add(new Point(0, -1));
			indexes.add(new Point(0, 1));
		}

		LinkedList<Point> openList = new LinkedList<Point>();
		LinkedList<Point> closedList = new LinkedList<Point>();
		openList.add(startIndex);
		parentList.put(startIndex, null);

		while (!openList.isEmpty()) {
			Point current = (Point) openList.removeFirst();
			if (current.equals(endIndex)) {
				return constructPath(endIndex);
			} else {
				closedList.add(current);
			}
			for (Point index : indexes) {
				Point neighborIndex = new Point(current.x + index.x, current.y + index.y);
				double dist0 = calculateDistanceBetweenPointsWithHypot(current.x, current.y, endIndex.x, endIndex.y);
				double dist1 = calculateDistanceBetweenPointsWithHypot(neighborIndex.x, neighborIndex.y, endIndex.x,
						endIndex.y);
				double distV = (dist0 - dist1);
				int dist = (int) Math.round(distV);
				if (dist >= 0) {
					if (neighborIndex.x >= 0 && neighborIndex.y >= 0) {
						int passable = World.isPassable(new Index(neighborIndex.x, neighborIndex.y));
						if (passable == 1) {
							if (!closedList.contains(neighborIndex) && !openList.contains(neighborIndex)) {
								parentList.put(neighborIndex, current);
								openList.add(neighborIndex);
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static double calculateDistanceBetweenPointsWithHypot(double x1, double y1, double x2, double y2) {
		double ac = Math.abs(y2 - y1);
		double cb = Math.abs(x2 - x1);
		return Math.hypot(ac, cb);
	}
}
