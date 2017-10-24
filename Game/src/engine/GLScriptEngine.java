package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

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
		
		//chunk = globals.loadfile(filename);
		String content;
		try {
			content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
			
			if(content.contains("function update("))
			{
				content += '\n'
						+ "update()";
						
				
			}
			if(content.contains("function setup("))
			{
				content+='\n'+
						"if not hasSetup then"+
						'\n'+
						"setup()"+
						'\n'+
						"hasSetup = true"+
						'\n'+
           			"end";
			}
			chunk = globals.load(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run() {
		if (chunk != null) {
			chunk.call();
		}
	}
}
