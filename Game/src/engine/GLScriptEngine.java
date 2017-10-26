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
		String content="";
		try {
			content='\n'+"argument = ...";
					
			content += new Scanner(new File(filename)).useDelimiter("\\Z").next();
			
			content +='\n'+
					"if (argument ~= '') then"+
					'\n'+
					"_G[argument]()"+
					'\n'+
					"end"+
					'\n';
			chunk = globals.load(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run(String func) {
		if (chunk != null) {
			chunk.call(LuaValue.valueOf(func));
		}
	}
	public void run() {
		if (chunk != null) {
			chunk.call("");
		}
	}
}
