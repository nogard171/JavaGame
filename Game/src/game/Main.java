package game;

public class Main {

	public static void main(String[] args) {
		try {
			new Game().start();
		} catch (Exception e) {
			System.out.println("Error2: " + e.getLocalizedMessage());
		}
	}
}