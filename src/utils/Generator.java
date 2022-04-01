package utils;

import java.util.Random;

public class Generator {
	public static String getRandom(int digits) {
		Random rand = new Random(); // instance of random class
		String total_characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String randomString = "";
		for (int i = 0; i < digits; i++) {
			int index = rand.nextInt(total_characters.length() - 1);
			randomString += total_characters.charAt(index);
		}
		return randomString;
	}
}
