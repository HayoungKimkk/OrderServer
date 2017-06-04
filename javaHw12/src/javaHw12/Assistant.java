package javaHw12;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Assistant {

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
							System.out.println("[Casher]: " + "�ֹ� ��ȣ: " + orderNumber + " �޴�: " + str + " �޾ҽ��ϴ�");
							orderNumber++;

							System.out.println("�ֹ��Ͻ� " + str + " �Ϸ�Ǿ����ϴ�.");
							dos.writeUTF("�ֹ��Ͻ� " + str + " �Ϸ�Ǿ����ϴ�.");
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

			System.out.println("�ý����� ���� �˴ϴ�.");
			socket.close();

		} catch (IOException e) {
			System.out.println("[C] ���� �߻�");
			e.printStackTrace();
		}

	}

}
