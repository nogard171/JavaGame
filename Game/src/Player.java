import java.io.Serializable;

public class Player implements Serializable
{
    public String username = "";
    public String characterName = "";
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public float yaw = 0;
    public State state = State.GAME;
}
