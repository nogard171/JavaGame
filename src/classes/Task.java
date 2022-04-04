package classes;

import java.awt.Point;
import java.util.LinkedList;

public class Task {
	public Path path;
	public TaskType type = TaskType.NONE;

	public boolean setup = false;
	public boolean complete = false;

	public Task(TaskType newType, Path newPath) {
		type = newType;
		path = newPath;
	}
}
