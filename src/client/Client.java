package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket_command = new Socket("127.0.0.1", 21);// 21端口监听命令
			Socket socket_file = new Socket("127.0.0.1", 4700);// 4700端口监听文件

			Scanner sin = new Scanner(System.in);
			String command = sin.next();
			String pathName = sin.next();

			File file = new File(pathName);

			ObjectOutputStream oos = new ObjectOutputStream(socket_command.getOutputStream());// 序列化
			oos.writeObject(command);
			oos.writeObject(file.getName());// 传输文件名

			FileInputStream fis = new FileInputStream(file);
			OutputStream os = socket_file.getOutputStream();

			int c;
			while ((c = fis.read()) != -1) {
				os.write(c);
			}
			os.close();// 关闭

			System.out.println("发送完了");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

// B:\Pictures\桌面壁纸\1.jpg
