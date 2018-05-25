package engine;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader {
	public static String getContent(String filename) {
		String contents = "";
		try {
			contents = new String(Files.readAllBytes(Paths.get(filename)));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	}
}
