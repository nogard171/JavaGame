package classes;

import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Color;

import core.*;

public class MenuInput extends MenuItem {
	public MenuInput(AFunction aFunction) {
		func = aFunction;
	}

	public String value = "";
}
