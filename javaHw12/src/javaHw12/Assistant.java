package javaHw12;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Assistant {

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

			dos.writeUTF("Assistant");

			int orderNumber = 0;
			String str = new String();
			try {
				while (!str.equals("Exit")) {
					str = dis.readUTF();

					if (str != null) {
						switch (str) {
						case "Shake":
						case "Icecream": {
							System.out.println("[Casher]: " + "주문 번호: " + orderNumber + " 메뉴: " + str + " 받았습니다");
							orderNumber++;

							System.out.println("주문하신 " + str + " 완료되었습니다.");
							dos.writeUTF("주문하신 " + str + " 완료되었습니다.");
						}
							break;

						default:
							dos.writeUTF(str);
							break;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("시스템이 종료 됩니다.");
			socket.close();

		} catch (IOException e) {
			System.out.println("[C] 문제 발생");
			e.printStackTrace();
		}

	}

}
