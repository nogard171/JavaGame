package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.GameData;

public class Loader {
	public static void loadConfig(String file) {
		try (InputStream input = new FileInputStream(file)) {
			GameData.config = new Properties();
			GameData.config.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
