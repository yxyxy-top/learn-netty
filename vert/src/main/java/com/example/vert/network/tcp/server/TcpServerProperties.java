package com.example.vert.network.tcp.server;

import com.example.vert.network.tcp.client.ValueObject;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.SocketAddress;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bsetfeng
 * @author zhouhao
 * @since 1.0
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TcpServerProperties implements ValueObject {

    private String id;

    private NetServerOptions options;

    private Map<String, Object> parserConfiguration = new HashMap<>();

    private String host;

    private int port;

    private boolean ssl;

    //服务实例数量(线程数)
    private int instance = Runtime.getRuntime().availableProcessors();

    private String certId;

    public SocketAddress createSocketAddress() {
        if (StringUtils.isEmpty(host)) {
            host = "localhost";
        }
        return SocketAddress.inetSocketAddress(port, host);
    }

    @Override
    public Map<String, Object> values() {
        return parserConfiguration;
    }
}
