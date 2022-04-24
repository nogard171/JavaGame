package classes;

import java.awt.Point;
import java.awt.Polygon;
import java.util.LinkedList;
import java.util.Random;

import data.AssetData;
import threads.BackEndThread;
import ui.UIInventory;
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
			switch (workingTask.type) {
			case MOVE:
				Path path = BackEndThread.getPath(workingTask.path);
				if (path != null) {
					if (path.steps != null) {
						if (path.steps.size() > 0) {
							workingTask.setup = true;
							complete = true;
						}
					}
				} else {
					workingTask.setup = true;
					workingTask.complete = true;
				}
				break;
			case CHOP:
			case MINE:
				Point index = workingTask.path.end;
				Object obj = World.getObjectAtIndex(new Index(index.x, index.y));
				if (obj != null) {
					ResourceData data = AssetData.resourceData.get(obj.getMaterial());
					if (data != null) {
						workingTask.path.steps = new LinkedList<Point>();
						for (int h = 0; h < data.hitpoints; h++) {
							workingTask.path.steps.add(index);
						}
						if (data.hitpoints == 0) {
							workingTask.path.steps.add(index);
						}
					}
				}
				workingTask.setup = true;
				break;
			default:
			}

		}
		return complete;
	}

	private static boolean process() {
		boolean complete = workingTask.complete;
		if (!complete) {
			switch (workingTask.type) {
			case MOVE:
				System.out.println("Move" + workingTask.path.steps.size());
				if (workingTask.path.steps.size() > 0) {
					Point index = workingTask.path.steps.removeFirst();
					Index newIndex = new Index(index.x, index.y);
					World.moveCharacter(newIndex);
				} else {
					workingTask.complete = true;
				}
				break;
			case CHOP:
			case MINE:
				if (workingTask.path.steps != null) {
					if (workingTask.path.steps.size() > 0) {
						workingTask.path.steps.removeFirst();
					}
					if (workingTask.path.steps.size() == 0) {
						Point index = workingTask.path.end;
						Object obj = World.getObjectAtIndex(new Index(index.x, index.y));
						if (obj != null) {

							ResourceData resourcedata = AssetData.resourceData.get(obj.getMaterial());
							if (resourcedata != null) {
								Random r = new Random();
								ResourceState state = resourcedata.states.get("HARVEST");
								if (state != null) {
									obj.setMaterial(state.material);
									obj.setModel(state.model);
									obj.bounds = new Polygon();
									World.setObjectAtIndex(index, obj);
									for (ItemDrop drop : state.items) {
										int dropCount = r.nextInt(drop.maxDrop - drop.minDrop + 1) + drop.minDrop;
										for (int d = 0; d < dropCount; d++) {
											ItemData itemData = AssetData.itemData.get(drop.item);
											System.out.println("test" + drop.item);
											if (itemData != null) {
												UIInventory.addItem(drop.item, dropCount);
											}
										}
									}
								}
							}
							workingTask.complete = true;
						}
					}
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
