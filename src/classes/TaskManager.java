package classes;

import java.awt.Point;
import java.util.LinkedList;

import threads.BackEndThread;
import utils.Ticker;

public class TaskManager {
	private static Ticker ticker;
	public static LinkedList<Task> tasks = new LinkedList<Task>();
	public static Task workingTask;

	public static void addTask(Task newTask) {
		if (newTask.path != null) {
			Path newPath = newTask.path;
			if (workingTask != null) {
				if (tasks.size() == 0) {
					newPath.start = workingTask.path.end;
				} else if (tasks.size() >= 0) {
					newPath.start = tasks.getLast().path.end;
				}
			}
			BackEndThread.findPath(newPath);
			tasks.add(newTask);
		}
	}

	public static void update() {
		if (ticker == null) {
			ticker = new Ticker();
		}
		ticker.update(200);
		if (ticker.hasTicked()) {
			
			System.out.println("Size:"+tasks.size());
			if (workingTask == null) {

				if (tasks.size() > 0) {
					workingTask = tasks.removeFirst();
				}
			}
			if (workingTask != null) {
				if (setup()) {
					if (process()) {
						workingTask = null;
					}
				}
			}
		}
	}

	private static boolean setup() {
		boolean complete = workingTask.setup;
		if (!complete) {

			Path path = BackEndThread.getPath(workingTask.path);
			if (path != null) {
				if (path.steps != null) {
					if (path.steps.size() > 0) {
						workingTask.setup = true;
						complete = true;
					}
				}
			}

		}
		return complete;
	}

	private static boolean process() {
		boolean complete = workingTask.complete;
		if (!complete) {
			switch (workingTask.type) {
			case MOVE:
				System.out.println("setup: " + workingTask.path.steps.size());
				if (workingTask.path.steps.size() > 0) {
					Point index = workingTask.path.steps.removeFirst();
					Index newIndex = new Index(index.x, index.y);
					World.moveCharacter(newIndex);
				} else {
					workingTask.complete = true;
				}
				break;
			default:
			}
		}
		return complete;

	}
}
