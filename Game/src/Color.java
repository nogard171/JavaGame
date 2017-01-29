
public class Color
{
	float red = 0;
	float green = 0;
	float blue = 0;
	float alpha = 0;
	public Color(float r, float g, float b,float a)
	{
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = a;
	}
	public Color(float r, float g, float b)
	{
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = 1;
	}
	public float getRed()
	{
		return red;
	}
	public float getGreen()
	{
		return green;
	}
	public float getBlue()
	{
		return blue;
	}
	public float getAlpha()
	{
		return alpha;
	}
}
