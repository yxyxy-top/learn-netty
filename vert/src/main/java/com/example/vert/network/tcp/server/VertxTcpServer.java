package com.example.vert.network.tcp.server;

import com.example.vert.network.DefaultNetworkType;
import com.example.vert.network.NetworkType;
import com.example.vert.network.tcp.client.TcpClient;
import com.example.vert.network.tcp.client.VertxTcpClient;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Slf4j
public class VertxTcpServer implements TcpServer {

    Collection<NetServer> tcpServers;

    @Setter
    private long keepAliveTimeout = Duration.ofMinutes(10).toMillis();

    @Getter
    private final String id;

    private final EmitterProcessor<TcpClient> processor = EmitterProcessor.create(false);

    private final FluxSink<TcpClient> sink = processor.sink(FluxSink.OverflowStrategy.BUFFER);

    public VertxTcpServer(String id) {
        this.id = id;
    }

    @Override
    public Flux<TcpClient> handleConnection() {
        return processor
            .map(Function.identity());
    }

    private void execute(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.warn("close tcp server error", e);
        }
    }

    public void setServer(Collection<NetServer> mqttServer) {
        if (this.tcpServers != null && !this.tcpServers.isEmpty()) {
            shutdown();
        }
        this.tcpServers = mqttServer;

        for (NetServer tcpServer : this.tcpServers) {
            tcpServer.connectHandler(this::acceptTcpConnection);
        }

    }


    protected void acceptTcpConnection(NetSocket socket) {
        if (!processor.hasDownstreams()) {
            log.warn("not handler for tcp client[{}]", socket.remoteAddress());
            socket.close();
            return;
        }
        VertxTcpClient client = new VertxTcpClient(id + "_" + socket.remoteAddress());
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
    }

    @Override
    public NetworkType getType() {
        return DefaultNetworkType.TCP_SERVER;
    }

    @Override
    public void shutdown() {
        if (null != tcpServers) {
            for (NetServer tcpServer : tcpServers) {
                execute(tcpServer::close);
            }
            tcpServers = null;
        }
    }

    @Override
    public boolean isAlive() {
        return tcpServers != null;
    }

    @Override
    public boolean isAutoReload() {
        return false;
    }
}
