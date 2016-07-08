package server;

import java.net.ServerSocket;

public class MultServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket_cmd = new ServerSocket(21);// 21端口监听命令
			while (true) {
				new ServerThread(serverSocket_cmd.accept()).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
