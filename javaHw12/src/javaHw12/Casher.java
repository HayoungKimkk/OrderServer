package javaHw12;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Casher {// main class

	ArrayList<String> clientsName;// declare client array name
	BufferedReader br;
	private static Socket socket;

	public static void main(String args[]) {// main method
		Casher casher = new Casher();
		casher.start();
	}// main end

	public static void sendMsg(String str) {//sendMsg method
		DataOutputStream dos;

			try {
				socket = new Socket();
				dos = new DataOutputStream(socket.getOutputStream());

				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e) {
				System.out.println("Send Error");
				e.printStackTrace();
			}
	}

	public void start() {// start method
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {// try-catch
			serverSocket = new ServerSocket(7002);
			System.out.println("Server is ready");

			int i = 0;

			while (i < 2) {// loop till connected
				System.out.println("waiting...");
				socket = serverSocket.accept();

				System.out.println("Connected from [" + socket.getInetAddress() + ":" + socket.getPort() + "]");
				i++;
			}
			
			String menu = new String();
			List<String> menuList = new ArrayList<String>();// add menu list

			menuList.add("Shake");
			menuList.add("Icecream");
			menuList.add("quit");

			while (!menu.equals("2")) {
				System.out.println("//Menu//");

				int idx = 0;
				for (String s : menuList) {
					System.out.println(idx++ + ": " + s);
				}

				System.out.print("주문하실 메뉴의 번호를 입력하세요: ");

				menu = br.readLine();

				switch (menu) {
				case "0":
				case "1":
				case "2": {
					int nIdx = Integer.parseInt(menu);
					sendMsg(menuList.get(nIdx));
					break;
				}
				default:
					System.out.println("없는 메뉴 입니다.!");
					break;
				}

				System.out.println("");
				System.out.println("");
				System.out.println("");
			}

		} catch (Exception e) {// catch exception
			e.printStackTrace();
		}
	}// start end

	class EchoServer extends Thread {// inner class declare
		Socket socket;
		InputStream in;
		OutputStream out;
		PrintWriter pw;
		String receiveMsg;

		EchoServer(Socket socket) {// Echo Server
			this.socket = socket;

			try {// input, output declare
				in = socket.getInputStream();
				out = socket.getOutputStream();
				pw = new PrintWriter(new OutputStreamWriter(out));
				br = new BufferedReader(new InputStreamReader(in));

				while ((receiveMsg = br.readLine()) != null) {
					System.out.println("Assistante: " + receiveMsg);
					pw.println(receiveMsg);
					pw.flush();
				}
				pw.close();
				br.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// receiver

	}// inner class end
}// main class end