package util;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

import Objects.OBJ;

public class OBJLoader {
	public OBJ getOBJ( String fileName) {
		OBJ obj = new OBJ();
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(fileName));

			// Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] data = line.split(" ");
				if (data[0].equals("v")) {
					obj.vectors.add(new Point(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
				}
				if (data[0].equals("s")) {
					obj.shadows.add(new Point(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
				}
				if (data[0].equals("f")) {
					if (obj.vpf == 4) {
						obj.faces.add(new Rectangle(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
								Integer.parseInt(data[3]), Integer.parseInt(data[4])));
					}
					if (obj.vpf == 3) {
						obj.faces.add(new Rectangle(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
								Integer.parseInt(data[3]), 0));
					}
				}
				if (data[0].equals("sc")) {
					int red = Integer.parseInt(data[1]);
					int green = Integer.parseInt(data[2]);
					int blue = Integer.parseInt(data[3]);
					int alpha = 255;
					if (data.length >=5) {
						alpha = Integer.parseInt(data[4]);
					}
					if (red > 255) {
						red = 255;
					} else if (red < 0) {
						red = 0;
					}
					if (green > 255) {
						green = 255;
					} else if (green < 0) {
						green = 0;
					}
					if (blue > 255) {
						blue = 255;
					} else if (blue < 0) {
						blue = 0;
					}
					if (alpha > 255) {
						alpha = 255;
					} else if (alpha < 0) {
						alpha = 0;
					}
					obj.shadow_Color = new Color(red, green, blue, alpha);
				}
				if (data[0].equals("vpf")) {
					obj.vpf = Integer.parseInt(data[1]);
				}
				if (data[0].equals("t")) {
					obj.textures.add(data[1]);
					obj.dark.add(Integer.parseInt(data[2]));
				}
				if (data[0].equals("shadow")) {
					obj.shadow = Boolean.parseBoolean(data[1]);
				}
				if (data[0].equals("c")) {
					Random random = new Random();
					int varity = 16;
					int red = random.nextInt((Integer.parseInt(data[1])) - (Integer.parseInt(data[1]) - varity) + 1)
							+ (Integer.parseInt(data[1]) + varity);
					int green = random.nextInt((Integer.parseInt(data[2])) - (Integer.parseInt(data[2]) - varity) + 1)
							+ (Integer.parseInt(data[2]) + varity);
					int blue = random.nextInt((Integer.parseInt(data[3])) - (Integer.parseInt(data[3]) - varity) + 1)
							+ (Integer.parseInt(data[3]) + varity);
					int alpha = 255;
					if (data.length >=5) {
						alpha = random.nextInt((Integer.parseInt(data[4])) - (Integer.parseInt(data[4]) - varity) + 1)
								+ (Integer.parseInt(data[4]) + varity);
					}
					if (red > 255) {
						red = 255;
					} else if (red < 0) {
						red = 0;
					}
					if (green > 255) {
						green = 255;
					} else if (green < 0) {
						green = 0;
					}
					if (blue > 255) {
						blue = 255;
					} else if (blue < 0) {
						blue = 0;
					}
					if (alpha > 255) {
						alpha = 255;
					} else if (alpha < 0) {
						alpha = 0;
					}
					obj.colors.add(new Color(red, green, blue, alpha));
				}
			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}
