package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GLLogger {
	private static String logFilename = "game.log";

	public static void write(String message) {
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFilename, true));
			writer.append('\n' + timeStamp.toString() + " - " + message);
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to write: '" + message + "' to log file.");
		}
	}
}
