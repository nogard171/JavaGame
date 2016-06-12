
import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;

import Objects.Account;
import Objects.MapData;
import Objects.NetworkData;
import Objects.ObjectData;

public class Client extends Thread
{
    static String	    sentence;
    static String	    modifiedSentence;

    static Socket	    clientSocket = null;
    static BufferedReader   inFromUser	 = null;
    static DataOutputStream outToServer	 = null;
    static DataInputStream  inFromServer = null;
    public String	    error	 = "";
    private int		    port	 = 2222;

    public void initilize() throws UnknownHostException, IOException
    {
	clientSocket = new Socket("localhost", port);
    }

    boolean    logged_in = false;
    public Map map	 = null;

    public Boolean login(String username, String password)
    {
	if (error == "")
	{

	    String hashtext = getHash(password);

	    NetworkData newData = new NetworkData("LOGIN");

	    Account newAccount = new Account(username, hashtext);
	    newData.account = newAccount;
	    SendData(newData);
	    System.out.println("Login Request Sent.");
	    try
	    {
		newData = getData();
	    }
	    catch (ClassNotFoundException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    catch (IOException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    if (newData == null)
	    {
		System.out.println("failed");
	    }
	    System.out.println("Login Request Received.");
	    if (newData.command.equals("OK"))
	    {
		this.map = new Map();
		System.out.println("Login Successful.");
		int newWidth = Math.round((Display.getWidth() / 32));
		int newHeight = Math.round((Display.getHeight() / 32)) + 1;
		getMap(0, 0, newHeight, newWidth);
		logged_in = true;
	    }
	    else if (newData.command.equals("FAILED"))
	    {
		System.out.println("Login Unsuccessful:" + newData.message);
		newData.message = "";
		logged_in = false;
	    }

	}
	else
	{
	    JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
	}
	return logged_in;
    }

    public void getMap(int i, int j, int newHeight, int newWidth)
    {
	// TODO Auto-generated method stub
	NetworkData mapdata = new NetworkData("MAP");
	mapdata.map = new MapData(i, j, newWidth, newHeight);
	SendData(mapdata);
    }

    public String getHash(String text)
    {
	MessageDigest md;
	String hashtext = "";
	try
	{
	    md = MessageDigest.getInstance("MD5");
	    byte[] messageDigest = md.digest(text.getBytes());
	    BigInteger number = new BigInteger(1, messageDigest);
	    hashtext = number.toString(16);
	}
	catch (NoSuchAlgorithmException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return hashtext;
    }

    public void run()
    {
	HashMap<String, String> options = new Config().getConfig("network");
	this.port = Integer.parseInt(options.get("port"));
	try
	{
	    initilize();
	}
	catch (ConnectException err)
	{
	    error = err.getMessage();
	}
	catch (UnknownHostException e)
	{
	    // TODO Auto-generated catch block
	    error = e.getMessage();
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    error = e.getMessage();
	}
	System.out.println("Initilized Connection.");
	clientLoop();
	System.out.println("Loop Terminated.");
	closeClient();
	System.out.println("Connection Terminated.");
    }

    public int mapCount = 0;

    public void clientLoop()
    {
	while (!logged_in)
	{
	    try
	    {
		Thread.sleep(10);
	    }
	    catch (InterruptedException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	while (logged_in)
	{
	    NetworkData data = new NetworkData("");
	    try
	    {
		data = getData();
	    }
	    catch (ClassNotFoundException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    catch (IOException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    String command = data.command;

	    if (data.command.startsWith("TILE"))
	    {
		if (data.map.chunks.containsKey(data.map.x + "," + data.map.y))
		{
		    for (int y = 0; y < 32; y++)
		    {
			for (int x = 0; x < 32; x++)
			{
			    ObjectData tile =data.map.chunks.get(data.map.x + "," + data.map.y).tiles
				    .get((x * 32) + "," + (y * 32));
			    if (tile != null)
			    {
				this.map.addTile(tile);
				System.out.println("tile("+(x * 32) + "," + (y * 32)+")");
			    }
			}
		    }
		}
		/*
		 * for (int i = 0; i < data.map.objectArray.size(); i++) {
		 * if (data.map.objectArray.get(i) != null) {
		 * this.map.addObject(data.map.objectArray.get(i));
		 * }
		 * }
		 */
	    }
	    if (command.startsWith("CLOSE/C"))
	    {
		break;
	    }
	}
    }

    public void closeClient()
    {

	try
	{
	    clientSocket.close();
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println("Client closed:\n" + error);
	System.exit(0);
    }

    ObjectOutputStream oos = null;

    public void SendData(NetworkData data)
    {
	try
	{
	    oos = new ObjectOutputStream(clientSocket.getOutputStream());

	    oos.writeObject(data);
	    oos.flush();

	    // DataOutputStream(clientSocket.getOutputStream());
	    // outToServer.writeUTF(message);
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    error = e.getLocalizedMessage();
	}
    }

    ObjectInputStream ois = null;

    public NetworkData getData() throws IOException, ClassNotFoundException
    {
	NetworkData newData = new NetworkData("");

	ois = new ObjectInputStream(clientSocket.getInputStream());

	newData = (NetworkData) ois.readObject();
	return newData;
    }

}