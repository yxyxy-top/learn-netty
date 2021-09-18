package com.example.vert.network.tcp.client;

import com.example.vert.network.Network;
import com.example.vert.network.tcp.TcpMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.time.Duration;

/**
 * TCP 客户端
 *
 * @author zhouhao
 * @version 1.0
 */
public interface TcpClient extends Network {

    /**
     * 获取客户端远程地址
     * @return 客户端远程地址
     */
    InetSocketAddress getRemoteAddress();

    /**
     * 订阅TCP消息,此消息是已经处理过粘拆包的完整消息
     *
     * @return TCP消息
     * @see
     */
    Flux<TcpMessage> subscribe();

    /**
     * 向客户端发送数据
     * @param message 数据对象
     * @return 发送结果
     */
    Mono<Boolean> send(TcpMessage message);

    void onDisconnect(Runnable disconnected);

    /**
     * 连接保活
     */
    void keepAlive();

    /**
     * 设置客户端心跳超时时间
     *
     * @param timeout 超时时间
     */
    void setKeepAliveTimeout(Duration timeout);

    /**
     * 重置
     */
    void reset();
}
