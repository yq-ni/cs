package com.yq.cs.client.engine;

import com.yq.cs.message.struct.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nyq on 2017/4/1.
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private ConcurrentHashMap<String, MessageFuture> messageFutureHashMap;
    private final HashMap<String, ChannelWrapper> channelWrapperMap;

    public ClientChannelInitializer(ConcurrentHashMap<String, MessageFuture> messageFutureHashMap, HashMap<String, ChannelWrapper> channelWrapperMap) {
        this.messageFutureHashMap = messageFutureHashMap;
        this.channelWrapperMap = channelWrapperMap;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                Response res = (Response)msg;
                String messageId = res.getMessageId();
                MessageFuture future = messageFutureHashMap.get(messageId);
                if (future != null) {
                    future.done(res);
                }
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                super.exceptionCaught(ctx, cause);
                channelWrapperMap.get(ctx.channel().remoteAddress().toString().substring(1)).setConnect(false);
            }
        });
    }


}
