package com.yq.cs.server.engine;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by nyq on 2017/4/2.
 */
public class BossChannelHandler extends ChannelDuplexHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyServer.getChannelMap().put(ctx.channel().remoteAddress().toString().substring(1), ctx.channel());
        super.channelRead(ctx, msg);
    }
}
