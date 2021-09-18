package com.learn.netty.client.redis;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;

public class RedisClientInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ChannelHandler());
        pipeline.addLast(new RedisBulkStringAggregator());
        pipeline.addLast(new RedisArrayAggregator());
        pipeline.addLast(new HandlerChannel());
        pipeline.addLast(new RedisClientHandler());
    }
}