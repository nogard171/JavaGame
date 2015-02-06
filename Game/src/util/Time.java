package util;

public class Time {
	private double seconds = 0;
	public void onUpdate()
	{
		this.seconds++;
	}
	public int getSeconds()
	{
		return (int) (this.seconds/100);
	}
}
