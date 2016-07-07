package server;

import java.net.ServerSocket;

public class MultServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(21);// 21端口监听命令
			while (true) {
				new ServerThread(serverSocket.accept(), new ServerSocket(4700)).start();// 4700端口监听文件
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
