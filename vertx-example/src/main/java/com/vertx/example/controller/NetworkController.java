package com.vertx.example.controller;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/12/7 13:48
 */
@RestController
@RequestMapping("Network")
public class NetworkController {

    private final Vertx vertx = Vertx.vertx();
    Map<String, NetServer> instances = new HashMap<>();

    @GetMapping("create/{port}")
    public String createNetwork(@PathVariable String port) {

        NetServer netServer = vertx.createNetServer();
        instances.put(port, netServer);
        netServer.connectHandler(sock -> {
            // 处理接收数据
            sock.handler(buffer -> {
                System.out.println("I received some bytes: " + buffer.length());
                sock.write("some data");
            }).closeHandler(v -> {
                // 处理关闭连接
                System.out.println("The socket has been closed");
            }).exceptionHandler(e -> {
                // 处理异常
                e.printStackTrace();
                sock.close();
            });
        }).listen(Integer.parseInt(port));
        return "success";
    }

    @GetMapping("close/{port}")
    public String closeNetwork(@PathVariable String port) {
        NetServer server = instances.get(port);
        server.close(socket -> {
            if (socket.succeeded()) {
                System.out.println("socket.succeeded()");
            } else {
                System.out.println("socket.fail()");
            }
        });
        return "success";
    }
}