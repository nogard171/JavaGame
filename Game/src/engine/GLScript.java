package engine;

import java.util.ArrayList;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GLScript extends GLComponent {
	private String filename = "";
	private GLDisplay window;
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
			scripter.sendGlobals("this", CoerceJavaToLua.coerce(obj));
			scripter.sendGlobals("keyboard", CoerceJavaToLua.coerce(new GLKeyboard()));
			scripter.sendGlobals("mouse", CoerceJavaToLua.coerce(new GLMouse()));
			scripter.sendGlobals("fps", CoerceJavaToLua.coerce(new GLFramesPerSecond()));
			this.loadProperties(obj);
			this.loadComponents(obj);
			scripter.loadScript(this.filename);
		}
	}
	
	public void loadProperties(GLObject obj)
	{
		ArrayList<GLProperty> props = obj.getProperties();
		for(int i =0;i<props.size();i++)
		{
			GLProperty property = obj.getProperty(props.get(i).getName());
			if(property!=null)
			{
				scripter.sendGlobals(props.get(i).getName(), CoerceJavaToLua.coerce(property));
			}
		}
	}

	
	public void loadComponents(GLObject obj)
	{
		ArrayList<GLComponent> coms = obj.getComponents();
		for(int i =0;i<coms.size();i++)
		{
			GLComponent component = obj.getComponent(coms.get(i).getName());
			if(component!=null)
			{
				scripter.sendGlobals(coms.get(i).getName(), CoerceJavaToLua.coerce(component));
			}
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
