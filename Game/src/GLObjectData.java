
public class GLObjectData {
	float[] vertices = {
	        -0.5f, 0.5f, 0f,    // Left top         ID: 0
	        -0.5f, -0.5f, 0f,   // Left bottom      ID: 1
	        0.5f, -0.5f, 0f,    // Right bottom     ID: 2
	        0.5f, 0.5f, 0f  // Right left       ID: 3
	};
	
	byte[] indices = {
	        // Left bottom triangle
	        0, 1, 2,
	        // Right top triangle
	        2, 3, 0
	};
	float[] colors = {
	        1f, 0f, 0f, 1f,
	        0f, 1f, 0f, 1f,
	        0f, 0f, 1f, 1f,
	        1f, 1f, 1f, 1f,
	};
}
