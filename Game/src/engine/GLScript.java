package engine;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GLScript extends GLComponent {
	private String filename = "";
	private GLDisplay window;
	GLFramesPerSecond fps;
	GLScriptEngine scripter;

	public GLScript() {
		this.setName("script");
	}

	public GLScript(String filename) {
		this.setFilename(filename);
		this.setName("script");
	}

	private void loadScript() {
		GLObject obj = this.getObject();
		if (obj != null) {
			scripter = new GLScriptEngine();
			fps = new GLFramesPerSecond();
			scripter.sendGlobals("this", CoerceJavaToLua.coerce(obj));
			scripter.sendGlobals("keyboard", CoerceJavaToLua.coerce(new GLKeyboard()));
			scripter.sendGlobals("mouse", CoerceJavaToLua.coerce(new GLMouse()));
			scripter.sendGlobals("fps", CoerceJavaToLua.coerce(fps));
			scripter.loadScript(this.filename);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void Run() {
		if(scripter==null)
		{
			 this.loadScript();
		}
		if(scripter!=null)
		{
			scripter.run();
		}
	}

	public GLDisplay getWindow() {
		return window;
	}

	public void setWindow(GLDisplay window) {
		this.window = window;
	}
}
