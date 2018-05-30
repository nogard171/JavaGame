package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import classes.GLSpriteData;

public class GLLoader {
	public static GLSpriteData getSpriteData() {
		GLSpriteData data = null;

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("file.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String line = br.readLine();

			while (line != null) {				
				line = br.readLine();
				
				String constructor = line.split(" ")[0];
				if(constructor.equals("i"))
				{
					System.out.println(line.split(" ")[1]);
				}
				
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return data;
	}
}
