package shaders;

import org.lwjgl.util.vector.Matrix4f;

import ToolBox.Maths;
import entities.Camera;
import entities.Light;

public class StaticShader extends ShaderProgram
{
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	
	public StaticShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0,"position");
		super.bindAttribute(1,"textureCoords");
		super.bindAttribute(2,"normal");
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix= super.getUniformLocation("viewMatrix");
		location_lightPosition= super.getUniformLocation("lightPosition");
		location_lightColour= super.getUniformLocation("lightColour");
		location_shineDamper= super.getUniformLocation("shineDamper");
		location_reflectivity= super.getUniformLocation("reflectivity");
	}
	
	public void loadShineVeriables(float damper,float reflectivity)
	{
		super.loadFloat(this.location_shineDamper,damper);
		super.loadFloat(this.location_reflectivity,reflectivity);
	}
	public void loadTransformmationMatric(Matrix4f matrix)
	{
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLight(Light light)
	{
		super.loadVector(this.location_lightPosition, light.getPosition());
		super.loadVector(this.location_lightColour, light.getColour());
	}
	public void loadProjectionMatrix(Matrix4f projection)
	{
		super.loadMatrix(location_projectionMatrix, projection);
	}
	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix= Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

}
