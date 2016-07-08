package server;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ServerThread extends Thread {
	Socket socket_cmd;

	ServerThread(Socket socket_cmd) {
		this.socket_cmd = socket_cmd;
	}

	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket_cmd.getInputStream());// 序列化
			String cmd = (String) ois.readObject();// 接收命令
			String ip_client = (String) ois.readObject();// 接收客户端ip
			int port_file = ois.readInt();// 接收客户端指定的端口
			Socket socket_file = new Socket(ip_client, port_file, InetAddress.getLocalHost(), 20);// 指定本机20端口去连接指定端口

			String pathName = (String) ois.readObject();// 接收文件绝对路径
			System.out.println(cmd + " " + pathName);

			OutputStream os = socket_file.getOutputStream();
			FileInputStream fis = new FileInputStream(pathName);

			int c;
			while ((c = fis.read()) != -1) {// 文件结束返回-1
				os.write(c);
			}

			os.close();// 关闭流，这样才能产生结束标志-1
			System.out.println("发送完了");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
