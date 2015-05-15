import java.awt.Graphics;

import util.TextureHandler;


public class Login {

	public void onUpdate(double d) {
		// TODO Auto-generated method stub
		
	}

	public void onPaint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(new TextureHandler().textureLoad(""),0,0,null);
		g.drawString("login",100,100);
	}

}
