package com.vertx.example.tcp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/12/7 10:28
 */
public class Client extends AbstractVerticle {


    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Verticle myVerticle = new Client();
        vertx.deployVerticle(myVerticle);

//        List<NetSocket> list = new ArrayList<>();
//        Client client = new Client();
//        for (int i = 0; i < 1000; i++) {
//            client.createClient(list,i);
//        }
    }

//    private void createClient(List<NetSocket> list,int i) {
//        Vertx vertx = Vertx.vertx();
//        NetClient client = vertx.createNetClient();
//        client.connect(8080, "localhost", res -> {
//            if (res.succeeded()) {
//                System.out.println("Connected!");
//                NetSocket socket = res.result();
//                socket.write("i = " + i);
//                System.out.println("add");
//                list.add(socket);
//            } else {
//                System.out.println("Failed to connect: " + res.cause().getMessage());
//            }
//        });
//    }
}