package objects;

public enum Action {
	Nothing, Move, Attack, Chop, Mine;

	public static Action[] actions = { Nothing, Move, Attack, Chop, Mine };
	public static String[] parsedActions = { "nothing", "move", "attack",
			"chop", "mine" };

	public static Action parseAction(String string) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Action currentAction = Action.Nothing;
		for (int i=0;i<parsedActions.length;i++) {
			if (string.toLowerCase().equals(parsedActions[i])) {
				currentAction = actions[i];
			}
		}
		return currentAction;
	}
}
