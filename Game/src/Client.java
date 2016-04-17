

import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

public class Client extends Thread {
	static String sentence;
	static String modifiedSentence;

	static Socket clientSocket = null;
	static BufferedReader inFromUser = null;
	static DataOutputStream outToServer = null;
	static DataInputStream inFromServer = null;
	public String error = "";

	public void initilize() throws UnknownHostException, IOException {
		clientSocket = new Socket("localhost", 2222);
	}

	boolean logged_in = false;

	public Boolean login(String username, String password) {
		if (error == "") {

			String hashtext = getHash(password);

			Data newData = new Data("LOGIN");
			Account newAccount = new Account(username, hashtext);
			newData.account = newAccount;
			SendData(newData);
			System.out.println("Login Request Sent.");
			newData = getData();
			System.out.println("Login Request Received.");
			if (newData.command.equals("OK")) {
				System.out.println("Login Successful.");
				logged_in = true;
			} else if (newData.command.equals("FAILED")) {
				System.out.println("Login Unsuccessful:" + newData.message);
				newData.message = "";
				logged_in = false;
			}

		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
		return logged_in;
	}

	public String getHash(String text) {
		MessageDigest md;
		String hashtext = "";
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(text.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			hashtext = number.toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashtext;
	}

	public void run() {
		try {
			initilize();
		} catch (ConnectException err) {
			error = err.getMessage();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			error = e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			error = e.getMessage();
		}
		System.out.println("Initilized Connection.");
		clientLoop();
		System.out.println("Loop Terminated.");
		closeClient();
		System.out.println("Connection Terminated.");
	}

	public void clientLoop() {
		while (!logged_in) {

		}
		while (true) {

			Data newData = getData();

			System.out.println("FROM SERVER: " + modifiedSentence);

			if (modifiedSentence.startsWith("CLOSE/C")) {
				break;
			}
			if (error != "") {
				break;
			}
		}
	}

	public void closeClient() {

		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client closed:\n" + error);
		System.exit(0);
	}

	public void SendData(Data data) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject(data);
			oos.flush();

			// DataOutputStream(clientSocket.getOutputStream());
			// outToServer.writeUTF(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			error = e.getLocalizedMessage();
		}
	}

	public Data getData() {
		Data newData = null;
		try {

			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			newData = (Data) ois.readObject();

			// inFromServer = new
			// DataInputStream(clientSocket.getInputStream());
			// String cmd = inFromServer.readUTF();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			error = e.getLocalizedMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			error = e.getLocalizedMessage();
		}
		return newData;
	}

}