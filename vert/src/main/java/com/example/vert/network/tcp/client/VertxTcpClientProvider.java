package com.example.vert.network.tcp.client;

import com.example.vert.metadata.ConfigMetadata;
import com.example.vert.network.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
public class VertxTcpClientProvider extends AbstractVerticle implements NetworkProvider<TcpClientProperties> {



    @Override
    public NetworkType getType() {
        return DefaultNetworkType.TCP_CLIENT;
    }


    @Override
    public VertxTcpClient createNetwork( TcpClientProperties properties) {
        VertxTcpClient client = new VertxTcpClient(properties.getId());

        initClient(client, properties);

        return client;
    }

    @Override
    public void reload( Network network,  TcpClientProperties properties) {
        initClient(((VertxTcpClient) network), properties);
    }

    public void initClient(VertxTcpClient client, TcpClientProperties properties) {
        NetClient netClient = vertx.createNetClient(properties.getOptions());
        client.setClient(netClient);
        client.setKeepAliveTimeoutMs(properties.getLong("keepAliveTimeout").orElse(Duration.ofMinutes(10).toMillis()));
        netClient.connect(properties.getPort(), properties.getHost(), result -> {
            if (result.succeeded()) {
                log.debug("connect tcp [{}:{}] success", properties.getHost(), properties.getPort());
                client.setSocket(result.result());
            } else {
                log.error("connect tcp [{}:{}] error", properties.getHost(), properties.getPort(),result.cause());
            }
        });
    }


    @Override
    public ConfigMetadata getConfigMetadata() {
        // TODO: 2019/12/19
        return null;
    }


    @Override
    public Mono<TcpClientProperties> createConfig( NetworkProperties properties) {
        return Mono.defer(() -> {
            TcpClientProperties config = new TcpClientProperties();
            config.setId(properties.getId());
            if (config.getOptions() == null) {
                config.setOptions(new NetClientOptions());
            }
            return Mono.just(config);
        });
    }
}
