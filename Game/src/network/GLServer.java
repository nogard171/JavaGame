package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;

public class GLServer extends Thread {

	private int PORT = 9090;
	private int MAX_CLIENTS = 10;
	private ServerSocket listener;
	public ArrayList<SocketHandler> sockets = new ArrayList<SocketHandler>();

	public void run() {
		try {
			this.listener = new ServerSocket(PORT);
			try {
				while (true) {
					if (this.MAX_CLIENTS > this.sockets.size()) {
						System.out.println("Waiting for Client...");
						SocketHandler socket = new SocketHandler(this.listener.accept());
						this.sockets.add(socket);
						this.sockets.get(this.sockets.size() - 1).start();
					}
					for (int i = 0; i < sockets.size(); i++) {
						this.sockets.get(i).sockets = this.sockets;
						if (sockets.get(i).isClosed()) {
							this.sockets.remove(i);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.listener.close();
		} catch (IOException e) {
			System.out.println("Server port is unavailable for use.");
		}
	}

	public void broadcastGLData(GLData data) {
		if (this.sockets.size() > 0) {
			for (SocketHandler handler : this.sockets) {
				if (handler != null) {
					if (data.ClientID != -1) {
						System.out.println("Client specific data found.");
						System.out.println("Client#" + handler.ID + "/" + data.ClientID);
						if (data.ClientID == handler.ID) {
							handler.sendGLData(data);
						}
					} else {
						handler.sendGLData(data);
					}
				}
			}
		} else {
			System.out.println("No clients to broadcast too.");
		}
	}
}
