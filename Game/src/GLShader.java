import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class GLShader {
	int pId = -1;
	private int vsId;
	private int fsId;

	public GLShader(String vertexFile, String fragmentFile) {
		// Load the vertex shader
		vsId = new GLLoader().loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		// Load the fragment shader
		fsId = new GLLoader().loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		// Create a new shader program that links both shaders
		pId = GL20.glCreateProgram();
		GL20.glAttachShader(pId, vsId);
		GL20.glAttachShader(pId, fsId);
	}

	public void setup() {
		GL20.glLinkProgram(pId);
		GL20.glValidateProgram(pId);

		if (GL11.glGetError() != GL11.GL_NO_ERROR) {
			System.out.println("ERROR - Could not create the shaders:");

		}
	}

	public void defineAttribLocation(int locationId, String name) {
		// Position information will be attribute 0
		GL20.glBindAttribLocation(pId, locationId, name);
	}

	public void Use() {
		GL20.glUseProgram(pId);
	}
}
