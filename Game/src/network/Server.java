package network;


import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {
	// The server socket.
		private static ServerSocket serverSocket = null;
		// The client socket.
		public static Socket clientSocket = null;

		// This chat server can accept up to maxClientsCount clients' connections.
		private static final int maxClientsCount = 10;
		private static final ServerClient[] threads = new ServerClient[maxClientsCount];
		public void run(){

			// The default port number.
			int portNumber = 81;
			System.out.println("Usage: java MultiThreadChatServer <portNumber>\n"
								+ "Now using port number=" + portNumber);

			/*
			 * Open a server socket on the portNumber (default 2222). Note that we
			 * can not choose a port less than 1023 if we are not privileged users
			 * (root).
			 */
			try {
				serverSocket = new ServerSocket(portNumber);
			} catch (IOException e) {
				System.out.println(e);
			}
			
			/*
			 * Create a client socket for each connection and pass it to a new
			 * client thread.
			 */
			while (true) {
				try {
					clientSocket = serverSocket.accept();
					int i = 0;
					for (i = 0; i < maxClientsCount; i++) {
						if (threads[i] == null) {
							
							(threads[i] = new ServerClient(clientSocket, threads))
									.start();
							threads[i].index =i;
							break;
						}
					}
					if (i == maxClientsCount) {
						PrintStream os = new PrintStream(
								clientSocket.getOutputStream());
						os.println("Server too busy. Try later.");
						os.close();
						clientSocket.close();
					}
				} catch (IOException e) {
					System.out.println(e);
				}
				
			}
			
		}
}
