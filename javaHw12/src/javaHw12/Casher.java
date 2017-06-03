package javaHw12;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Casher {

	ArrayList<String> clientsName;// declare client array name

	public static void main(String args[]) {//main method
		new Casher().start();
	}//main end

	public void Cahser() {//Class method
		clientsName = new ArrayList<String>();
	}//Class method end

	public void start(){//start method
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try{
			serverSocket = new ServerSocket(7002);
			System.out.println("Server is ready");
			
			while(true){
				System.out.println("waiting...");
				socket = serverSocket.accept();
				
				System.out.println("Connected from [" + socket.getInetAddress() + ":" + socket.getPort() + "]");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}//start end
	
	class ServerReceiver extends Thread {//inner class declare
		Socket socket;
		ObjectInputStream in;
		ObjectOutputStream out;
		String name;
		
		ServerReceiver(Socket socket){//receiver
			this.socket = socket;
			
			try{//input, output declare
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
			} catch(Exception e){
			}
		}//receiver end
		
		public void run(){
			
		}
	}
}