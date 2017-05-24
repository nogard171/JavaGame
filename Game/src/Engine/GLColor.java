package Engine;

public class GLColor {
	public float red = 0;
	public float green = 0;
	public float blue = 0;
	public float alpha = 0;

	public GLColor(float r, float g, float b) {
		this.setColor(r, g, b, 1);
	}

	public GLColor(float r, float g, float b, float a) {
		this.setColor(r, g, b, a);
	}

	public void setColor(float r, float g, float b, float a) {
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = a;
	}
	public float getRed()
	{
		return this.red;
	}
	public float getGreen()
	{
		return this.green;
	}
	public float getBlue()
	{
		return this.blue;
	}
	public float getAlpha()
	{
		return this.alpha;
	}
	
}
