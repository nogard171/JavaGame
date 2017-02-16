import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

public class Person
{
	public ArrayList<Entity>  body = new ArrayList<Entity>();
	
	private Vector2f position = new Vector2f(0,0);
	private Vector2f origin = new Vector2f(0,0);
	int leftRot = 0;
	public void animateLeg()
	{
		ArrayList<Entity> limbs = this.getLimb("frontLeg");
		
		limbs.get(0).setRotation(leftRot);
	}
	public ArrayList<Entity> getLimb(String name)
	{
		ArrayList<Entity> limbs = new ArrayList<Entity>();
		for(int i =0;i<body.size();i++)
		{
			if(body.get(i).getName()==name)
			{
				limbs.add(body.get(i));
			}
		}
		return limbs;
	}
	public void Walk()
	{
		leftRot++;
		animateLeg();
	}
}
