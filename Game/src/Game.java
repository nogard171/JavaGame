import components.GLTransform;
import engine.GLDisplay;

public class Game {
	private GLDisplay display;
	
	public void Start() {
		this.display = new GLDisplay(800,600);
		this.display.Create();
		
		GLTransform test = new GLTransform();
		
		System.out.println("Type: " +test.GetType());
		
		Setup();
		GameLoop();
	}

	public void Setup() {

	}
	
	public void GameLoop()
	{
		while(!display.IsClosed())
		{
			this.Update();
			this.Render();
		}
	}

	public void Update() {
		display.Update();
	}

	public void Destroy() {
	}

	public void Render() {
		
	}
}
