package com.example.vert.network.tcp.client;

import com.example.vert.network.tcp.TcpMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;

import java.nio.charset.StandardCharsets;

@Slf4j
public abstract class AbstractTcpClient implements TcpClient {

    private EmitterProcessor<TcpMessage> processor = EmitterProcessor.create(false);

    protected void received(TcpMessage message) {
        if (processor.getPending() > processor.getBufferSize() / 2) {
            log.warn("not handler,drop tcp message:{}", message.getPayload().toString(StandardCharsets.UTF_8));
            return;
        }
        processor.onNext(message);
    }

}
