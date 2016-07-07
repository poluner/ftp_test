package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket_command = new Socket("127.0.0.1", 21);// 21端口监听命令

			Scanner sin = new Scanner(System.in);
			String command = sin.next();
			String pathName = sin.next();

			File file = new File(pathName);

			ObjectOutputStream oos = new ObjectOutputStream(socket_command.getOutputStream());// 序列化
			oos.writeObject(command);
			oos.writeObject(file.getName());// 传输文件名

			int port_file = new ObjectInputStream(socket_command.getInputStream()).readInt();// 接收服务器给出的高端端口号
			Socket socket_file = new Socket("127.0.0.1", port_file);// 文件传输端口号由服务器给出

			FileInputStream fis = new FileInputStream(file);
			OutputStream os = socket_file.getOutputStream();

			int c;
			while ((c = fis.read()) != -1) {// 文件结束返回-1
				os.write(c);
			}
			os.close();// 一定要刷新，close的时候会自动刷新

			System.out.println("发送完了");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
// 端口号范围[0,65535]
// B:\Pictures\桌面壁纸\1.jpg
