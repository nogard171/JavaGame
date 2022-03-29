package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import data.EngineData;
import data.Settings;

public class SettingsHandler {
	public static boolean load() {
		File configFile = new File(Settings.settingsFile);
		if (configFile.exists()) {
			try {
				FileReader reader = new FileReader(configFile);
				Properties props = new Properties();
				props.load(reader);
				loadData(props);
				reader.close();
			} catch (IOException e) {
				return false;
			}
			return true;
		}
		return false;
	}

	private static boolean loadData(Properties props) {
		// window data

		EngineData.width = (props.containsKey("window.width") ? Integer.parseInt(props.getProperty("window.width"))
				: EngineData.width);
		EngineData.height = (props.containsKey("window.height") ? Integer.parseInt(props.getProperty("window.height"))
				: EngineData.height);

		EngineData.isFullscreen = (props.containsKey("window.fullscreen")
				? Boolean.parseBoolean(props.getProperty("window.fullscreen"))
				: EngineData.isFullscreen);
		EngineData.isVsync = (props.containsKey("window.vsync")
				? Boolean.parseBoolean(props.getProperty("window.vsync"))
				: EngineData.isVsync);

		EngineData.inactiveFPS = (props.containsKey("window.inactive_fps")
				? Integer.parseInt(props.getProperty("window.inactive_fps"))
				: EngineData.inactiveFPS);
		EngineData.pauseFPS = (props.containsKey("window.menu_fps")
				? Integer.parseInt(props.getProperty("window.menu_fps"))
				: EngineData.pauseFPS);
		EngineData.targetFPS = (props.containsKey("window.target_fps")
				? Integer.parseInt(props.getProperty("window.target_fps"))
				: EngineData.targetFPS);

		EngineData.userFolder = (props.containsKey("user.folder") ? props.getProperty("user.folder")
				: EngineData.userFolder);
		EngineData.worldFolder = (props.containsKey("user.world_folder") ? props.getProperty("user.world_folder")
				: EngineData.worldFolder);

		Settings.localChunks = (props.containsKey("world.localChunks")
				? Boolean.parseBoolean(props.getProperty("world.localChunks"))
				: Settings.localChunks);
		Settings.infinate = (props.containsKey("world.infinate")
				? Boolean.parseBoolean(props.getProperty("world.infinate"))
				: Settings.infinate);

		Settings.textureFile = (props.containsKey("asset.textures") ? props.getProperty("asset.textures")
				: Settings.textureFile);
		Settings.materialsFile = (props.containsKey("asset.materials") ? props.getProperty("asset.materials")
				: Settings.materialsFile);
		
		EngineData.quality = (props.containsKey("window.quality") ? props.getProperty("window.quality")
				: EngineData.quality);

		return true;
	}

	public static void save() {
		File configFile = new File(Settings.settingsFile);
		try {
			if (configFile.exists()) {
				System.out.println("delete");
				configFile.delete();
			}
			if (!configFile.exists()) {
				configFile.createNewFile();
			}

			Properties props = new Properties();
			saveData(props);
			FileWriter writer = new FileWriter(configFile);
			props.store(writer, "Game Settings");
			writer.close();
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		}
	}

	private static void saveData(Properties props) throws IOException {
		// window data
		props.setProperty("window.width", EngineData.width + "");
		props.setProperty("window.height", EngineData.height + "");
		props.setProperty("window.fullscreen", EngineData.isFullscreen + "");
		props.setProperty("window.vsync", EngineData.isVsync + "");

		props.setProperty("window.inactive_fps", EngineData.inactiveFPS + "");
		props.setProperty("window.menu_fps", EngineData.pauseFPS + "");
		props.setProperty("window.target_fps", EngineData.targetFPS + "");

		props.setProperty("user.folder", EngineData.userFolder + "");
		props.setProperty("user.world_folder", EngineData.worldFolder + "");

		props.setProperty("world.local_chunks", Settings.localChunks + "");
		props.setProperty("world.infinate", Settings.infinate + "");

		props.setProperty("asset.textures", Settings.textureFile + "");
		props.setProperty("asset.materials", Settings.materialsFile + "");
		props.setProperty("window.quality", EngineData.quality + "");
	}
}
