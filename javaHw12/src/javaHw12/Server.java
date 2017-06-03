package javaHw12;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		Socket socket;
		String hostIp = "localhost";
		int port = 7002;
		BufferedReader br;
		InputStream in;
		String echo;

		try {
			socket = new Socket(hostIp, port);
			in = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			while (true) {
				echo = br.readLine();
				if (echo.equals("quit")) {
					break;
				} else {
					System.out.println(echo);
				}
				br.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}