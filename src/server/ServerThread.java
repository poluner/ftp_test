package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerThread extends Thread {
	Socket socket_command;

	ServerThread(Socket socket_command) {
		this.socket_command = socket_command;
	}

	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket_command.getInputStream());// 序列化
			String command = (String) ois.readObject();
			String fileName = (String) ois.readObject();
			System.out.println(command + " " + fileName);

			// 接收到命令之后给客户端发送一个随机的端口号
			int port_file = new Random().nextInt(65535 - 1025 + 1) + 1025;// 高端端口号范围[1025,65535]
			ObjectOutputStream oos = new ObjectOutputStream(socket_command.getOutputStream());// 向客户端发送端口号
			oos.writeInt(port_file);
			oos.close();// 一定要刷新一下（close会自动刷新），否则另一端会一直阻塞

			ServerSocket serverSocket_file = new ServerSocket(port_file);// 服务器打开该端口
			Socket socket_file = serverSocket_file.accept();// 等待文件传输的连接

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
