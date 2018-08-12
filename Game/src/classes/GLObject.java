package classes;

public class GLObject {
	private GLBound bounds = new GLBound(0, 0, 100, 50);
	private boolean visible = false;
	private GLSize frameSize = new GLSize(1, 1);
	private int[][] frames;

	public GLObject() {
		frames = new int[frameSize.getWidth()][frameSize.getHeight()];
	}
}
