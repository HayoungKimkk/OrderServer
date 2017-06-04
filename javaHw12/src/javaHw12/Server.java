package javaHw12;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("���! Address �� Port ��  �Է����ּ���");
			return;
		}

		try {
			String host = args[0];
			int port = Integer.parseInt(args[1]);

			System.out.println("[C] ����(" + host + ":" + port + ")�� ���� ��...");
			Socket socket = new Socket(host, port);

			System.out.println("[C] " + socket.getRemoteSocketAddress() + "�� �����");

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
									.println("[Casher]: " + "�ֹ� ��ȣ: " + orderNumber + " �޴�: " + strRecvMsg + " �޾ҽ��ϴ�");
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

				System.out.println("�ý����� ����˴ϴ�.");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			System.out.println("[C] ���� �߻�");
			e.printStackTrace();
		}
	}
}
