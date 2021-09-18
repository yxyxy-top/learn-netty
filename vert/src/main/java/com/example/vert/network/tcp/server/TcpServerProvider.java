package com.example.vert.network.tcp.server;

import com.example.vert.metadata.ConfigMetadata;
import com.example.vert.network.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TcpServerProvider extends AbstractVerticle implements NetworkProvider<TcpServerProperties> {

    @Override
    public NetworkType getType() {
        return DefaultNetworkType.TCP_SERVER;
    }

    @Override
    public VertxTcpServer createNetwork(TcpServerProperties properties) {

        VertxTcpServer tcpServer = new VertxTcpServer(properties.getId());
        initTcpServer(tcpServer, properties);

        return tcpServer;
    }

    private void initTcpServer(VertxTcpServer tcpServer, TcpServerProperties properties) {
        int instance = Math.max(2, properties.getInstance());
        List<NetServer> instances = new ArrayList<>(instance);
        for (int i = 0; i < instance; i++) {
            instances.add(vertx.createNetServer(properties.getOptions()));
        }
        tcpServer.setServer(instances);
        tcpServer.setKeepAliveTimeout(properties.getLong("keepAliveTimeout", Duration.ofMinutes(10).toMillis()));
        for (NetServer netServer : instances) {
            netServer.listen(properties.createSocketAddress(), result -> {
                if (result.succeeded()) {
                    log.info("tcp server startup on {}", result.result().actualPort());
                } else {
                    log.error("startup tcp server error", result.cause());
                }
            });
        }
    }

    @Override
    public void reload(Network network, TcpServerProperties properties) {
        VertxTcpServer tcpServer = ((VertxTcpServer) network);
        tcpServer.shutdown();
        initTcpServer(tcpServer, properties);
    }

    @Override
    public ConfigMetadata getConfigMetadata() {
        return null;
    }

    @Override
    public Mono<TcpServerProperties> createConfig(NetworkProperties properties) {
        return Mono.defer(() -> {
            TcpServerProperties config = new TcpServerProperties();
            config.setId(properties.getId());
            if (config.getOptions() == null) {
                config.setOptions(new NetServerOptions());
            }
            return Mono.just(config);
        });
    }
}
