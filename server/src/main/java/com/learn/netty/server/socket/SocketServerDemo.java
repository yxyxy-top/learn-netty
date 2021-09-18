package com.learn.netty.server.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/9/29 17:06
 */
public class SocketServerDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6111);
        Socket socket = serverSocket.accept();
        System.out.println("客户端 " + socket.getInetAddress().getHostAddress() + "连接成功！");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String userName = bufferedReader.readLine();
        String password = bufferedReader.readLine();

        if(("userName").equals(userName)&&("password").equals(password)){
            //需要将响应返回给客户端
            //System.out.println("登陆成功");
            bufferedWriter.write("登陆成功");
            bufferedWriter.flush();
        }else{
            bufferedWriter.write("登陆失败");
            bufferedWriter.flush();
        }

        socket.close();
    }
}