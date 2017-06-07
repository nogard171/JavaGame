package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Server extends Thread {
	private int PORT = 9090;
	ServerSocket listener;
	PrintWriter out;
	ArrayList<SocketHandler> sockets = new ArrayList<SocketHandler>();

	public void run() {

		try {
			listener = new ServerSocket(PORT);
			try {
				while (true) {
					System.out.println("Waiting for Client...");
					// Socket socket = listener.accept();
					new SocketHandler(listener.accept()).start();
					// SocketHandler socket = new
					// SocketHandler(listener.accept());
					// sockets.add(socket);
					// sockets.get(sockets.size()-1).run();
					// out = new PrintWriter(socket.getOutputStream(), true);
					// out.println(new Date().toString());
					// socket.close();
					/*
					 * for (int i = 0; i < sockets.size(); i++) {
					 * sockets.get(i).sockets = this.sockets; }
					 */
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listener.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
