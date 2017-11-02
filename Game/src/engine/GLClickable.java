package engine;

public class GLClickable extends GLComponent {
	public GLMouse mouse = new GLMouse();
	GLRectangle bounds = new GLRectangle();
	public boolean hovered = false;
	public boolean clicked = false;
	private String scriptFile = "";
	GLScript script = null;
	public GLClickable(String newScript) {
		this.scriptFile=newScript;
		this.setName("clickable");
	}
	public GLClickable() {
		this.setName("clickable");
	}
	public void setScript()
	{
		script = new GLScript(scriptFile);
		script.setObject(this.getObject());
	}
	private void Action(String func) {
		if(script==null&&scriptFile!="")
		{
			this.setScript();
		}
		if (script != null) {
			script.Run(func);
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
						this.Action("hovered");
					} else {
						hovered = false;
						//this.Action("unhovered");
					}
					
					
					
					if (this.hovered && mouse.isMouseDown(0)&&clickCount==0) {
						clicked = true;
						clickCount++;
						this.Action("click");
					} 
					else if (mouse.isMouseUp(0)&&clickCount>0) {
						clickCount=0;
						this.Action("unclick");
					}
				}
			}
		}
	}
}
