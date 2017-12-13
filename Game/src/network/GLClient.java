package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class GLClient extends Thread {
	private boolean close = false;
	private byte[] serverAddress = new byte[] { 127, 0, 0, 1 };
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public boolean started = false;

	public void run() {
		try {
			InetAddress inet = InetAddress.getByAddress(serverAddress);
			if (inet.isReachable(5000)) {
				try {
					String serverAddressString = serverAddress[0] + "." + serverAddress[1] + "." + serverAddress[2]
							+ "." + serverAddress[3];
					System.out.println("Server Address:" + serverAddressString);
					this.clientSocket = new Socket(serverAddressString, 9090);

					this.oos = new ObjectOutputStream(this.clientSocket.getOutputStream());
					this.ois = new ObjectInputStream(this.clientSocket.getInputStream());
					this.started = true;
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
							} else if (data.protocol == GLProtocol.TRANSFORM) {
								GLSyncTransform syncTransform = (GLSyncTransform) data;
								System.out.println("Transform (" + data.ClientID + "): " + syncTransform.position.toString());
							} else if (data.protocol == GLProtocol.CLOSE_CONNECTION) {
								GLMessage message = (GLMessage) data;
								System.out.println(message.from + " : " + message.message);
								this.close = true;
							} else {
								System.out.println("Data received Protocol:" + data.protocol);
							}
						}
					}
					this.ois.close();
					this.oos.close();
					this.clientSocket.close();

				} catch (IOException e) {
					System.out.println("Server not available for connection.");
				}
			} else {
				System.out.println("Unable to reach server.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(0);
	}

	private GLData readGLData() throws ClassNotFoundException, IOException {
		GLData data = null;
		if (this.ois != null) {

			data = (GLData) this.ois.readObject();
		}
		return data;
	}

	public void sendGLData(GLData data) {
		if (data != null) {
			try {
				this.oos.reset();
				this.oos.writeObject(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("empty");
		}
	}
}
