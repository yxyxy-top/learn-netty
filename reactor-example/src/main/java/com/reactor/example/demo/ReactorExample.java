package com.reactor.example.demo;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutor;
import reactor.netty.DisposableServer;
import reactor.netty.FutureMono;
import reactor.netty.tcp.TcpServer;

/**
 * Reactor 例子
 *
 * @author XuYu
 * @date 2021-04-22 10:55
 */
public class ReactorExample {

    public static void main(String[] args) {
        int listenPort = 9919;
        ChannelGroup group = new DefaultChannelGroup(new DefaultEventExecutor());

        DisposableServer server =
                TcpServer.create()
                         .port(listenPort)
                         .doOnConnection(connection -> {
                             // only when client is connected, this is OK
                             group.add(connection.channel());
                             System.out.println("connection:" + connection);
                         })
                         .wiretap(true)
                         .observe((connection, newState) -> {
                             // only called when connection is connected and configured, but not disconnected
                             // this is a BUG ?
                             System.out.println("connection:" + connection + ", newState=" + newState);
                         })
                         .handle((inbound, outbound) -> outbound.sendString(inbound.receive().asString()).neverComplete())
                         .bindNow();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            FutureMono.from(group.close()).block();
            server.disposeNow();
        }));

        server.onDispose().block();

    }

}