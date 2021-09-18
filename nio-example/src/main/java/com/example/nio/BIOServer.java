package com.example.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author XuYu
 * @date 2021-04-27 17:05
 */
public class BIOServer {
    public void initBioServer(int port) {
        //服务端Socket
        ServerSocket serverSocket = null;
        // 客户端Socket
        Socket socket = null;
        BufferedReader reader = null;
        String inputContent;
        int count = 0;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(stringNowTime() + ": serverSocket started");
            while (true) {
                // 等待请求
                socket = serverSocket.accept();
                System.out.println(stringNowTime() + ": id为 " + socket.hashCode() + "的Clientsocket connected");
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while ((inputContent = reader.readLine()) != null) {
                    System.out.println("收到id为 " + socket.hashCode() + " " + inputContent);
                    count++;
                }
                System.out.println("id为 " + socket.hashCode() + "的Clientsocket " + stringNowTime() + "读取结束");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String stringNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static void main(String[] args) {
        BIOServer server = new BIOServer();
        server.initBioServer(9898);
    }
}