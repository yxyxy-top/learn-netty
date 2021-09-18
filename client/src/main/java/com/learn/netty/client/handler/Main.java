package com.learn.netty.client.handler;

import com.learn.netty.client.config.NettyClient;
import com.learn.netty.client.entity.request.RpcRequest;
import io.netty.channel.Channel;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 1104; i++) {
            startClient(i);
        }
    }

    private static void startClient(int i) throws Exception {
        NettyClient client = new NettyClient("127.0.0.1", 8080);
        //启动client服务
        client.start();

        Channel channel = client.getChannel();
        //channel对象可保存在map中，供其它地方发送消息
//        for (int j = 0; j < 100; j++) {
            //消息体
            RpcRequest request = new RpcRequest();
            request.setId(UUID.randomUUID().toString());
            request.setData("客户端消息: " + i /*+ "消息数： "+ j*/);
            channel.writeAndFlush(request);
//        }
    }
}