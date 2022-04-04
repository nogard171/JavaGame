package classes;

import java.awt.Point;
import java.util.LinkedList;

public class Path {
	public Point start;
	public Point end;
	public LinkedList<Point> steps = new LinkedList<Point>();

	public Path(Point inputStart, Point inputEnd) {
		start = inputStart;
		end = inputEnd;
	}
}
