import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GLObject {
	GLObjectData data;
	private int vaoId = 0;
	private int vboId = 0;
	private int vboiId = 0;
	private int vbocId = 0;
	private int indicesCount = 0;
	
	public GLObject(GLObjectData newData)
	{
		this.data = newData;
	}

	public FloatBuffer getFloatBuffer(float[] data) {
		// Sending data to OpenGL requires the usage of (flipped) byte buffers
		FloatBuffer newBuffer = BufferUtils.createFloatBuffer(data.length);
		newBuffer.put(data);
		newBuffer.flip();

		return newBuffer;
	}

	public ByteBuffer getByteBuffer(byte[] data) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(data.length);
		newBuffer.put(data);
		newBuffer.flip();

		return newBuffer;
	}

	public int getBuffer(FloatBuffer buffer, int index, int count) {
		// Create a new VBO for the indices and select it (bind) - COLORS
		int newId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, newId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, count, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		return newId;
	}

	public void setupQuad() {

		FloatBuffer verticesBuffer = getFloatBuffer(data.vertices);

		ByteBuffer indicesBuffer = getByteBuffer(data.indices);
		indicesCount = data.indices.length;

		FloatBuffer colorsBuffer = getFloatBuffer(data.colors);

		// Create a new Vertex Array Object in memory and select it (bind)
		// A VAO can have up to 16 attributes (VBO's) assigned to it by default
		vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);

		// Create a new Vertex Buffer Object in memory and select it (bind)
		// A VBO is a collection of Vectors which in this case resemble the
		// location of each vertex.
		vboId = getBuffer(verticesBuffer, 0, 3);

		vbocId = getBuffer(colorsBuffer, 1, 4);

		// Deselect (bind to 0) the VAO
		GL30.glBindVertexArray(0);

		vboiId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		// Deselect (bind to 0) the VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

	}

	public void Render() {
		// Bind to the VAO that has all the information about the quad vertices
		GL30.glBindVertexArray(vaoId);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		// Bind to the index VBO that has all the information about the order of
		// the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);

		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public void Destroy() {
		// Delete the index VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboiId);

		GL20.glDisableVertexAttribArray(0);

		// Delete the VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboId);

		// Delete the VAO
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoId);
	}
}
