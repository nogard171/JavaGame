package core;

public class GLMaskObject extends GLObject {
	private GLObject maskObject;

	public void setMask(GLObject newMaskObject) {
		this.maskObject = newMaskObject;
	}

	public GLObject getMask() {
		return this.maskObject;
	}
}
