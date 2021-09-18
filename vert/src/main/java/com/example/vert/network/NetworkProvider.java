package com.example.vert.network;

import com.example.vert.metadata.ConfigMetadata;
import reactor.core.publisher.Mono;


/**
 * 网络组件支持提供商
 *
 * @param <P> 网络组件类型
 */
public interface NetworkProvider<P> {

    /**
     * @return 类型
     * @see DefaultNetworkType
     */
    NetworkType getType();

    /**
     * 使用配置创建一个网络组件
     *
     * @param properties 配置信息
     * @return 网络组件
     */
    Network createNetwork( P properties);

    /**
     * 重新加载网络组件
     *
     * @param network    网络组件
     * @param properties 配置信息
     */
    void reload( Network network,  P properties);

    /**
     * @return 配置定义元数据
     */
    ConfigMetadata getConfigMetadata();

    /**
     * 根据可序列化的配置信息创建网络组件配置
     *
     * @param properties 原始配置信息
     * @return 网络配置信息
     */
    Mono<P> createConfig( NetworkProperties properties);

}
