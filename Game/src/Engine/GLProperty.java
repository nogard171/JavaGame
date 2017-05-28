package Engine;

public class GLProperty {
	private String name = "";
	private String value = "";
	private GLPropertyType type = GLPropertyType.STRING;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String newValue)
	{
		this.type =  GLPropertyType.STRING;
		this.value = newValue;
	}
	public void setIntValue(int newValue)
	{
		this.type =  GLPropertyType.INT;
		this.setValue(String.valueOf(newValue));
	}
	public void setBoolValue(boolean newValue)
	{
		this.type =  GLPropertyType.BOOL;
		this.setValue(String.valueOf(newValue));		
	}

	public String getStringValue() {
		return this.value;
	}
	public int getIntValue()
	{
		return Integer.parseInt(this.value);
	}
	public boolean getBoolValue()
	{
		return Boolean.parseBoolean(this.value);
	}
}
