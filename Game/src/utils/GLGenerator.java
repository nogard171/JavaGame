package utils;

import java.util.UUID;

public class GLGenerator {
	public static String generateObjectID() {
		return UUID.randomUUID().toString();
	}
}
