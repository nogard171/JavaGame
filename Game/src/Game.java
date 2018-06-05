import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game extends GLDisplay {

	GLObject obj;
	
	GLShader shader;
	
	@Override
	public void Setup() {
		super.Setup();
		
		shader = new GLShader("resources/shaders/vertex.glsl","resources/shaders/fragment.glsl");
		shader.defineAttribLocation(0, "in_Position");
		shader.defineAttribLocation(1, "in_Color");		
		shader.setup();
		
		
		
		obj = new GLObject(new GLObjectData());
		//obj.setupQuad();
		obj.setupInterleavedQuad();
	}

	@Override
	public void Update() {
		super.Update();
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
		       GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
		       GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
	}

	@Override
	public void Render() {
		super.Render();
		shader.Use();
	
		
		
		obj.Render();
	}

	@Override
	public void Destroy() {
		obj.Destroy();
		super.Destroy();
	}


}
