import java.awt.Point;
import java.awt.Rectangle;

public class GLObject {

	String type = "GRASS";
	String name ="GRASS";
	Rectangle bounds = new Rectangle(0,0,0,0);
	Point[] polygon;

	public GLObject(String newType) {
		this.type= newType;
	}

	public GLObject(String newName, String newType) {
		this.name = newName;
		this.type= newType;
		
	}
}
