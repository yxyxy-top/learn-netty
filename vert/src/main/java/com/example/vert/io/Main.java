package com.example.vert.io;

import io.vertx.core.Vertx;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/9/28 14:25
 */
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MainVerticle.class.getName());
        /*NetServerOptions options = new NetServerOptions();
        NetServer server = Vertx.vertx().createNetServer(options);
        server.connectHandler(socket ->{
            socket.handler(buffer -> {
                System.out.println(buffer.toString(CharsetUtil.US_ASCII));
                System.out.println("I received some bytes: " + buffer.length());
                socket.write(Buffer.buffer("阿什顿发给"));
            });
            socket.closeHandler(v -> System.out.println("The socket has been closed"));
        });
        server.listen(SocketAddress.inetSocketAddress(8080, "127.0.0.1"),result -> {
            if (result.succeeded()) {
                System.out.println("tcp server startup on "+result.result().actualPort());
            } else {
                System.out.println("startup tcp server error"+result.cause());
            }
        });*/

    }
}