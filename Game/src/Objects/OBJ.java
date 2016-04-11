package Objects;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class OBJ {
	public String title = "";
	public int Size = 32;
	public ArrayList<Point> vectors = new ArrayList<Point>();
	public ArrayList<Point> shadows = new ArrayList<Point>();
	public ArrayList<Rectangle> faces = new ArrayList<Rectangle>();
	public ArrayList<String> textures = new ArrayList<String>();
	public ArrayList<Integer> dark = new ArrayList<Integer>();
	public ArrayList<Color> colors = new ArrayList<Color>();
	public boolean shadow = false;
	public int vpf = 0;
	public Color shadow_Color = new Color(0,0,0,224);
}
