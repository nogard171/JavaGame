package game;

public class Game {
	AssetLibrary library;

	public void start() {
		library = new AssetLibrary();
		library.loadAssetLibrary();
		Material mat = library.getMaterial("test");
		if (mat != null) {
			System.out.println("name: " + mat.getName());
		}
	}
}
