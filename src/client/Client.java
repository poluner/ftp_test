package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket_cmd = new Socket("172.16.11.197", 21);// 21端口监听命令

			Scanner sin = new Scanner(System.in);
			String cmd = sin.next();
			int port_file = sin.nextInt();// 输入本机墙外端口
			ObjectOutputStream oos = new ObjectOutputStream(socket_cmd.getOutputStream());// 序列化
			oos.writeObject(cmd);
			oos.writeObject(InetAddress.getLocalHost().getHostAddress());// 发送本机ip
			oos.writeInt(port_file);// 发送本机墙外端口
			oos.flush();// 一定要刷新
			ServerSocket serverSocket_file = new ServerSocket(port_file);// 在这个墙外端口监听
			Socket socket_file = serverSocket_file.accept();// 与服务器连接

			String pathName = sin.next();// 想要在服务器下载的文件的绝对路径
			oos.writeObject(pathName);// 传输服务器中文件的绝对路径
			oos.close();// 关闭流！

			FileOutputStream fos = new FileOutputStream(new File(pathName).getName());// 接收的文件放在相对路径
			InputStream is = socket_file.getInputStream();

			int c;
			while ((c = is.read()) != -1) {// 流结束返回-1
				fos.write(c);
			}
			fos.close();

			System.out.println("接收完了");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
// 端口号范围[0,65535]
// B:\Pictures\桌面壁纸\1.jpg
