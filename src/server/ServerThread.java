package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
	Socket socket_command;
	ServerSocket serverSocket_file;

	ServerThread(Socket socket_command, ServerSocket serverSocket_file) {
		this.socket_command = socket_command;
		this.serverSocket_file = serverSocket_file;
	}

	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket_command.getInputStream());// 序列化
			String command = (String) ois.readObject();
			String fileName = (String) ois.readObject();
			System.out.println(command + " " + fileName);

			Socket socket_file = serverSocket_file.accept();
			InputStream is = socket_file.getInputStream();

			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);

			int c;
			while ((c = is.read()) != -1) {// 只有客户端的os.close()执行之后这里才可以返回-1
				fos.write(c);
			}

			fos.close();// 文件输出流关闭才有权限打开查看
			System.out.println("接收完了");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
