package Engine;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.lwjgl.input.Keyboard;

public class GLScript extends GLComponent {
	private String filename = "";
	private GLWindow window;

	public GLScript() {
		this.setName("script");
	}

	public GLScript(String filename) {
		this.setFilename(filename);
		this.setName("script");
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void Run() {
		GLObject obj = this.getObject();		
		if (obj != null) {
			
			Globals globals = JsePlatform.standardGlobals();
			
			globals.set("this", CoerceJavaToLua.coerce(obj));
			
			//globals.set("keyboard", CoerceJavaToLua.coerce());
			
			LuaValue chunk = globals.loadfile(filename);
			chunk.call();
		}
	}
	public GLWindow getWindow() {
		return window;
	}
	public void setWindow(GLWindow window) {
		this.window = window;
	}
}
