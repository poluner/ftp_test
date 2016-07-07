package server;

import java.net.ServerSocket;

public class MultServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket_command = new ServerSocket(21);// 21端口监听命令
			ServerSocket serverSocket_file = new ServerSocket(4700);// 4700端口监听文件
			while (true) {
				new ServerThread(serverSocket_command.accept(), serverSocket_file.accept()).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
