package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class SocketHandler extends Thread {

	Socket clientSocket;
	ArrayList<SocketHandler> sockets = new ArrayList<SocketHandler>();

	public SocketHandler(Socket newClient) {
		this.clientSocket = newClient;
	}

	public void run()  {
		System.out.println("Client connected");
		try {
			while (true) {
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				out.println(new Date().toString());
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				if (out.checkError()) {
					break;
				}
			}
			System.out.println("Client closed");
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
