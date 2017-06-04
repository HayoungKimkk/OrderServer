package javaHw12;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("경고! Address 와 Port 를  입력해주세요");
			return;
		}

		try {
			String host = args[0];
			int port = Integer.parseInt(args[1]);

			System.out.println("[C] 서버(" + host + ":" + port + ")에 연결 중...");
			Socket socket = new Socket(host, port);

			System.out.println("[C] " + socket.getRemoteSocketAddress() + "에 연결됨");

			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			dos.writeUTF("Server");

			int orderNumber = 0;
			String strRecvMsg = new String();
			try {
				while (!strRecvMsg.equals("Exit")) {
					strRecvMsg = dis.readUTF();

					if (strRecvMsg != null) {
						switch (strRecvMsg) {
						case "Shake":
						case "Icecream": // Casher msg
						{
							System.out
									.println("[Casher]: " + "주문 번호: " + orderNumber + " 메뉴: " + strRecvMsg + " 받았습니다");
							orderNumber++;
						}
							break;

						case "Exit":
							break;

						default: // Assistant msg
							System.out.println(strRecvMsg);
							break;

						}

					}
				}

				System.out.println("시스템이 종료됩니다.");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			System.out.println("[C] 문제 발생");
			e.printStackTrace();
		}
	}
}
