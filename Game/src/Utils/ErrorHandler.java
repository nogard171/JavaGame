package Utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class ErrorHandler {
	private static String logfile = "system/logs/errors.log";

	public static void LogError(String error) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			Writer output;
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logfile, true), "UTF-8"));
			output.append('\n' + dateFormat.format(date) + " Error: " + error);
			output.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error:", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
