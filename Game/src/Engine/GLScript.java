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
	LuaValue chunk;
	GLFramesPerSecond fps;

	private void loadScript() {
		GLObject obj = this.getObject();
		if (obj != null) {
			
			fps = new GLFramesPerSecond();
			
			Globals globals = JsePlatform.standardGlobals();

			globals.set("this", CoerceJavaToLua.coerce(obj));

			globals.set("keyboard", CoerceJavaToLua.coerce(new GLKeyboard()));

			globals.set("fps", CoerceJavaToLua.coerce(fps));
			
			chunk = globals.loadfile(filename);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void Run() {
		if(chunk==null)
		{
			this.loadScript();
		}
		if (chunk != null) {
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
