package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Entity {
	public OBJ obj = new OBJ();
	public int x = 100;
	public int y = 100;
	public int shadow_x = 100;
	public int shadow_y = 100;
	public int float_height = 12;

	public Entity(int i, int j) {
		this.x = i;
		this.y = j;
		this.shadow_x = i;
		this.shadow_y = j;
	}

	public Entity() {

	}

	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		if (obj.shadow) {
			int dist = this.shadow_y - this.y;
			if (dist < float_height) {
				Polygon polygon = new Polygon();
				polygon.addPoint(obj.shadows.get(0).x + this.shadow_x,
						obj.shadows.get(0).y + this.shadow_y + 48 + (dist / 2) + 8);
				polygon.addPoint(obj.shadows.get(1).x + this.shadow_x - (dist) - 16,
						obj.shadows.get(1).y + this.shadow_y + 48);
				polygon.addPoint(obj.shadows.get(2).x + this.shadow_x,
						obj.shadows.get(2).y + this.shadow_y + 48 - (dist / 2) - 8);
				polygon.addPoint(obj.shadows.get(3).x + this.shadow_x + (dist) + 16,
						obj.shadows.get(3).y + this.shadow_y + 48);
				g.setColor(obj.shadow_Color);
				g.fillPolygon(polygon);
				g.setColor(Color.black);
				g.drawPolygon(polygon);
			}
		}
		for (int f = 0; f < obj.faces.size(); f++) {

			Polygon polygon = new Polygon();
			polygon.addPoint(obj.vectors.get(obj.faces.get(f).x).x + this.x,
					obj.vectors.get(obj.faces.get(f).x).y + this.y);
			polygon.addPoint(obj.vectors.get(obj.faces.get(f).y).x + this.x,
					obj.vectors.get(obj.faces.get(f).y).y + this.y);
			polygon.addPoint(obj.vectors.get(obj.faces.get(f).width).x + this.x,
					obj.vectors.get(obj.faces.get(f).width).y + this.y);
			if (obj.vpf == 4) {
				polygon.addPoint(obj.vectors.get(obj.faces.get(f).height).x + this.x,
						obj.vectors.get(obj.faces.get(f).height).y + this.y);
			}
			if (obj.textures.size() > 0) {
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File( obj.textures.get(f)));
				} catch (IOException e) {
				}
				
				int darken = obj.dark.get(f);
				for (int x = 0; x < img.getWidth(); x++) {
					for (int y = 0; y < img.getHeight(); y++) {

						Color col = new Color(img.getRGB(x, y), true);
						if (darken >= 0) {
							for (int d = 0; d < darken; d++) {
								col = col.darker();
							}
						} else {
							for (int d = 0; d < -darken; d++) {
								col = col.brighter();
							}
						}
						img.setRGB(x, y, col.getRGB());
					}
				}
				 

				TexturePaint paint = new TexturePaint(img,
						new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight()));
				g2d.setPaint(paint);
				g2d.fillPolygon(polygon);
			} else {
				g.setColor(obj.colors.get(f));
				g.fillPolygon(polygon);
			}
			g.setColor(Color.black);
			g2d.drawPolygon(polygon);

		}
	}
}
