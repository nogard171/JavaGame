package engine;

public class GLClickable extends GLComponent {
	public GLMouse mouse = new GLMouse();
	GLRectangle bounds = new GLRectangle();
	public boolean hovered = false;
	public boolean clicked = false;
	private String clickScriptFile = "";
	GLScript clickScript = null;
	private String unclickScriptFile = "";
	GLScript unclickScript = null;
	public GLClickable(String newClickScript, String newUnclickScript) {
		this.clickScriptFile=newClickScript;
		this.unclickScriptFile=newUnclickScript;
		this.setName("clickable");
	}
	public GLClickable() {
		this.setName("clickable");
	}
	public void setScript()
	{
		clickScript = new GLScript(clickScriptFile);
		clickScript.setObject(this.getObject());
		unclickScript = new GLScript(unclickScriptFile);
		unclickScript.setObject(this.getObject());
	}
	public void Click() {
		if(clickScript==null&&clickScriptFile!="")
		{
			this.setScript();
		}
		if (clickScript != null) {
			clickScript.Run();
		}	
	}
	public void Unclick() {
		if(unclickScript==null&&unclickScriptFile!="")
		{
			this.setScript();
		}
		if (unclickScript != null) {
			unclickScript.Run();
		}	
	}
	int clickCount = 0;
	public void Run() {
		if (this.getObject() != null) {
			GLTransform transform = (GLTransform) this.getObject().getComponent("transform");
			if (transform != null) {
				GLMaterial mat = (GLMaterial) this.getObject().getComponent("material");
				if (mat != null) {
					this.bounds = new GLRectangle(transform.getPosition().getX(), transform.getPosition().getY(),
							mat.getFrameSize().getWidth(), mat.getFrameSize().getHeight());
					if (this.bounds.inside(new GLRectangle(mouse.getMouseX(), mouse.getMouseY(), 1, 1))) {
						hovered = true;
					} else {
						hovered = false;
					}

					if (this.hovered && mouse.isMouseDown(0)&&clickCount==0) {
						clicked = true;
						clickCount++;
						this.Click();
					} 
					else if (this.hovered && mouse.isMouseUp(0)) {
						clickCount=0;
						clicked = false;
						this.Unclick();
					}
					else {
						clicked = false;
					}
				}
			}
		}
	}
}
