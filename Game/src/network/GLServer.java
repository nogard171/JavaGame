package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;

public class GLServer extends Thread {
	private int PORT = 9090;
	private ServerSocket listener;
	ArrayList<SocketHandler> sockets = new ArrayList<SocketHandler>();

	public void run() {
		try {
			this.listener = new ServerSocket(PORT);
			try {
				while (true) {
					System.out.println("Waiting for Client...");
					SocketHandler socket = new SocketHandler(this.listener.accept());
					this.sockets.add(socket);
					this.sockets.get(this.sockets.size() - 1).start();

					for (int i = 0; i < sockets.size(); i++) {
						this.sockets.get(i).sockets = this.sockets;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.listener.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void broadcastGLData(GLData data) {
		for (SocketHandler handler : this.sockets) {
			handler.sendGLData(data);
		}
	}
}
