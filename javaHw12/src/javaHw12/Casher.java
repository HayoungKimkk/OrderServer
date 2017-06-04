package javaHw12;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Casher extends Thread {

	private static ServerSocket serverSocket;

	static List<Socket> socketList = new ArrayList<Socket>();
	static DataOutputStream dosServer = null;

	public Casher(int port) {
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(0);
		} catch (SocketException e) {
			System.out.println("[s] 타임아웃 설정 실패");
		} catch (IOException e) {
			System.out.println("[s] 소켓 생성 실패");
		}
	}

	public static void main(String[] args) {

		int port = Integer.parseInt(args[0]);

		Casher casher = new Casher(port);
		casher.setDaemon(true);
		casher.start();

		List<String> menuList = new ArrayList<String>();

		menuList.add("Shake");
		menuList.add("Icecream");
		menuList.add("Exit");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String menu = new String();

		while (!menu.equals("2")) // == 종료 신호
		{
			System.out.println("//Menu//");

			// print a menulist
			int idx = 0;
			for (String s : menuList) {
				System.out.println(idx++ + ": " + s);
			}

			System.out.print("주문하실 메뉴의 번호를 입력하세요: ");

			try {
				menu = br.readLine();
			} catch (IOException e) {

			}

			switch (menu) {
			case "0": // Shake
			case "1": // Icecream
			case "2": // Exit
			{
				int idxMenu = Integer.parseInt(menu);
				sendMsg(menuList.get(idxMenu));
			}
				break;

			default:
				System.out.println("없는 메뉴 입니다.!");
				break;
			}

			System.out.println("///////////////");

		}

		// 클라이언트 소켓 접속 대기....

		System.out.println("시스템이 종료됩니다.");
	}

	public static void sendMsg(String str) {
		for (Socket s : socketList) {
			try {
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());

				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e) {
				System.out.println("Send Error");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		int clientNumber = 0;

		while (true) {
			try {
				System.out.println("[Casher] " + serverSocket.getLocalPort() + " 포트로 접속 기다리는 중...");

				Socket socket = serverSocket.accept();

				System.out.println("[S] " + (clientNumber + 1) + "번째로  " + socket.getRemoteSocketAddress() + "에서 연결함");

				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

				socketList.add(clientNumber, socket);

				Thread echoThread = new Thread() {
					@Override
					public void run() {
						// Server, Assistant 판단
						String strProgram = null;
						while (strProgram == null) {
							try {
								strProgram = dis.readUTF();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						switch (strProgram) {
						case "Server":
							dosServer = dos;
							break;

						case "Assistant":
							String strRecvMsg = new String("");
							while (!strRecvMsg.equals("Exit")) {
								try {
									strRecvMsg = dis.readUTF();
									if (strRecvMsg == null)
										continue;

									switch (strRecvMsg) {
									case "Exit":
										break;

									default:
										if (dosServer != null) {
											dosServer.writeUTF("[Assistant]: " + strRecvMsg);
										}
										break;
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									break;
								}
							}

							break;
						}
					}
				};

				echoThread.setDaemon(true);
				echoThread.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			clientNumber++;
		}
	}

}
