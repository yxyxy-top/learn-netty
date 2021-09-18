package com.vertx.example.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/12/7 09:39
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Server server = new Server();
        server.createTcpServer();
    }


    Map<String, NetSocket> sockets = new HashMap<>();

    public void createTcpServer() {
        Vertx vertx = Vertx.vertx();
        NetServer netServer = vertx.createNetServer();
        netServer.connectHandler(sock -> {
            // 处理接收数据
            sock.handler(buffer -> {
                System.out.println("I received some bytes: " + buffer);
                sock.write("some data");
            }).closeHandler(v -> {
                // 处理关闭连接
                System.out.println("The socket has been closed");
            }).exceptionHandler(e -> {
                // 处理异常
                e.printStackTrace();
                sock.close();
            });
        }).listen(12345);
    }
}