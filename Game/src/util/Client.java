package util;

import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import Objects.Data;

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

	public void login(String username, String password) {
		if (error == "") {
			MessageDigest md;
			String hashtext = "";
			try {
				md = MessageDigest.getInstance("MD5");

				byte[] messageDigest = md.digest(password.getBytes());
				BigInteger number = new BigInteger(1, messageDigest);
				hashtext = number.toString(16);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Data newData = new Data("LOGIN", username + "/" + hashtext);
			System.out.println("User:" + username + "/pass:" + hashtext);
			SendData(newData);
			System.out.println("Login Successful.");
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
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
		try {
			while (!logged_in) {

			}
			while (true) {

				modifiedSentence = inFromServer.readUTF();

				System.out.println("FROM SERVER: " + modifiedSentence);

				if (modifiedSentence.startsWith("CLOSE/C")) {
					break;
				}
				if (error != "") {
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
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
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newData;
	}

}