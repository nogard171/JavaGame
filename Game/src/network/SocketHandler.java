package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class SocketHandler extends Thread {

	boolean close = false;
	Socket clientSocket;
	ArrayList<SocketHandler> sockets = new ArrayList<SocketHandler>();
	ObjectInputStream ois;
	ObjectOutputStream oos;
	private int ID = -1;

	public SocketHandler(Socket newClient) {
		this.clientSocket = newClient;
	}

	public void run() {
		System.out.println("Client connected");
		for (int index = 0; index < this.sockets.size(); index++) {
			if (this != this.sockets.get(index)) {
				this.ID = index;
			}
		}
		try {
			oos = new ObjectOutputStream(this.clientSocket.getOutputStream());
			ois = new ObjectInputStream(this.clientSocket.getInputStream());
			while (!close) {
				GLData data = null;
				try {
					data = readGLData();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					this.close = true;
				}

				if (data != null) {
					this.broadcastGLData(data);
				}
			}
			System.out.println("Client closed");

			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadcastGLData(GLData data) {
		for (SocketHandler handler : this.sockets) {
			if (this != handler) {
				handler.sendGLData(data);
			}
		}
	}

	public GLData readGLData() throws ClassNotFoundException, IOException {
		GLData data = null;
		data = (GLData) ois.readObject();
		return data;
	}

	public void sendGLData(GLData data) {
		try {
			data.ClientID = this.ID;
			oos.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
