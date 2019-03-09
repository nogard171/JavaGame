package core;

import java.util.HashMap;

public class GLObjectStack {
	//ArrayList<GLItem> groundItems = new ArrayList<GLItem>();
	private int layerMax = 0; 
	HashMap<Integer, GLObject> objects = new HashMap<Integer, GLObject>();
	public void addObject(GLObject obj, int layerIndex)
	{
		objects.put(layerIndex, obj);
		if(layerIndex>layerMax)
		{
			layerMax = layerIndex;
		}
		
		if(layerIndex==0)
		{
			layerMax = 1;
		}
	}
	public void removeObjectAtIndex(int layerIndex)
	{
		objects.replace(layerIndex, null);
	}
	public void removeObject(GLObject obj)
	{
		for(GLObject arrayObj :objects.values())
		{
			if(arrayObj.getHash()==obj.getHash())
			{
				objects.remove(arrayObj);
				break;
			}
		}
	}
	public int getLayerMax()
	{
		return this.layerMax;
	}
	public GLObject getObjectAtIndex(int layerIndex)
	{
		return this.objects.get(layerIndex);
	}
}
