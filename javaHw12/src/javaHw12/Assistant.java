package javaHw12;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Assistant {
	public static void main(String[] args) {
		Socket socket;
		String hostIp = "localhost";
		int port = 7002;
		BufferedReader br;
		InputStream in;
		OutputStream out;
		PrintWriter pw;
		String echo;

		try {
			socket = new Socket(hostIp, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			pw = new PrintWriter(new OutputStreamWriter(out));
			br = new BufferedReader(new InputStreamReader(in));
			while (true) {
				echo = br.readLine();
				if (echo.equals("quit")) {
					break;
				} else {
					while (true) {
						System.out.println(echo + "is ordered");
						pw.println(echo + "done");
						pw.flush();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}