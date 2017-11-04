package engine;

public class GLPhysics extends GLComponent{
	private GLPhysicType type= GLPhysicType.NONE;

	public GLPhysics() {
		this.setName("physics");
	}
	public GLPhysicType getType() {
		return type;
	}

	public void setType(GLPhysicType type) {
		this.type = type;
	}
	public void Run() {
		
	}
	
}
