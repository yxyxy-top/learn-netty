package com.learn.netty.client.handler;

import com.learn.netty.client.entity.response.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
 
    //处理服务端返回的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) {
        System.out.println("接受到server响应数据: " + response.toString());
    }
 
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
 
 
}