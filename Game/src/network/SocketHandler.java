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
	private Socket clientSocket;
	ArrayList<SocketHandler> sockets = new ArrayList<SocketHandler>();
	ObjectInputStream ois;
	ObjectOutputStream oos;
	public int ID = -1;

	public SocketHandler(Socket newClient) {
		this.clientSocket = newClient;
	}

	public String getIP() {
		return this.clientSocket.getRemoteSocketAddress().toString();
	}

	public void run() {
		System.out.println("Client connected");
		for (int index = 0; index < this.sockets.size(); index++) {
			if (this == this.sockets.get(index)) {
				this.ID = index;
				break;
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
					this.close = true;
				}

				if (data != null) {
					this.broadcastGLData(data);
				}
			}
			System.out.println("Client# " + this.ID + " closed");
			clientSocket.close();
			sockets.remove(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isClosed() {
		return this.close;
	}

	public void broadcastGLData(GLData data) {
		for (SocketHandler handler : this.sockets) {
			// if (this != handler) {
			data.ClientID = this.ID;
			handler.sendGLData(data);
			// }
		}
	}

	public GLData readGLData() throws ClassNotFoundException, IOException {
		GLData data = null;
		data = (GLData) this.ois.readObject();
		return data;
	}

	public void sendGLData(GLData data) {
		if (oos != null && data != null) {
			try {
				this.oos.reset();
				this.oos.writeObject(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
