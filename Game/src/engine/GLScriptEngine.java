package engine;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class GLScriptEngine {
	LuaValue chunk;
	Globals globals;

	public GLScriptEngine() {
		globals = JsePlatform.standardGlobals();
	}

	public void sendGlobals(String name, LuaValue object) {
		globals.set(name, object);
		globals.checkglobals();
	}

	public void loadScript(String filename) {
		chunk = globals.loadfile(filename);
	}
	public void run() {
		if (chunk != null) {
			chunk.call();
		}
	}
}
