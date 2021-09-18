package com.learn.netty.client.redis;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * TODO
 *
 * @author XuYu
 * @date 2020/9/28 11:09
 */
public class HandlerChannel extends MessageToMessageEncoder {
    /**
     * Encode from one message to an other. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToMessageEncoder} belongs to
     * @param msg the message to encode to an other one
     * @param out the {@link List} into which the encoded msg should be added
     *            needs to do some kind of aggregation
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        System.out.println(msg);
        System.out.println(out.toString());
    }
}