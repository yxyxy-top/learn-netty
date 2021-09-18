package com.example.vert.controller;

import com.example.vert.network.tcp.client.TcpClient;
import com.example.vert.network.tcp.client.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.SocketAddress;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.UUID;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/9/30 11:07
 */
@RestController
@Slf4j
public class DemoController {

    static Vertx vertx = Vertx.vertx();

    @Setter
    private long keepAliveTimeout = Duration.ofMinutes(10).toMillis();

    private final EmitterProcessor<TcpClient> processor = EmitterProcessor.create(false);

    private final FluxSink<TcpClient> sink = processor.sink(FluxSink.OverflowStrategy.BUFFER);

    @RequestMapping("send")
    public void send(){

        NetServerOptions options = new NetServerOptions().setPort(1111);
        NetServer netServer = vertx.createNetServer(options);
        netServer.connectHandler(socket -> {
/*            if (!processor.hasDownstreams()) {
                System.out.println("not handler for tcp client[{}]"+socket.remoteAddress());
                socket.close();
                return;
            }*/
            VertxTcpClient client = new VertxTcpClient(UUID.randomUUID() + "_" + socket.remoteAddress());
            client.setKeepAliveTimeoutMs(keepAliveTimeout);
            try {
                socket.exceptionHandler(err -> {
                    log.error("tcp server client [{}] error", socket.remoteAddress(), err);
                }).closeHandler((nil) -> {
                    log.debug("tcp server client [{}] closed", socket.remoteAddress());
                    client.shutdown();
                });
                client.setSocket(socket);
                sink.next(client);
                log.debug("accept tcp client [{}] connection", socket.remoteAddress());
            } catch (Exception e) {
                log.error("create tcp server client error", e);
                client.shutdown();
            }
        });
        netServer.listen(SocketAddress.inetSocketAddress(1111, "localhost"), result -> {
            if (result.succeeded()) {
                System.out.println("tcp server startup on "+result.result().actualPort());
            } else {
                System.out.println("startup tcp server error "+result.cause());
            }
        });

    }

}