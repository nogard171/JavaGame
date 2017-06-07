package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
	public void run() {
		String serverAddress = "localhost";
		Socket s;
		try {
			s = new Socket(serverAddress, 9090);
			while (true) {
				BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String answer = input.readLine();
				System.out.println("Time:" + answer);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.exit(0);
	}
}
