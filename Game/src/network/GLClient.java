package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class GLClient extends Thread {
	private boolean close = false;
	private String serverAddress = "localhost";
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public void run() {
		try {
			this.clientSocket = new Socket(serverAddress, 9090);

			this.oos = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.ois = new ObjectInputStream(this.clientSocket.getInputStream());

			while (!this.close) {
				GLData data = null;
				try {
					data = this.readGLData();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					this.close = true;
				}
				if (data != null) {
					if (data.protocol == GLProtocol.MESSAGE) {
						GLMessage message = (GLMessage) data;
						System.out.println(message.from + " said: " + message.message);
					} else {
						System.out.println("Data received Protocol:" + data.protocol);
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private GLData readGLData() throws ClassNotFoundException, IOException {
		GLData data = null;
		data = (GLData) this.ois.readObject();
		return data;
	}

	public void sendGLData(GLData data) {
		try {
			this.oos.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
