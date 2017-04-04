package com.yq.cs.server.engine;

import com.yq.cs.message.serialize.SelectCodec;
import com.yq.cs.message.struct.Request;

import com.yq.cs.server.config.ServerConfigs;
import com.yq.cs.server.config.ServerIPAddrConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nyq on 2017/3/31.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private SelectCodec selectCodec = new SelectCodec();
    private ServerConfigs serverConfigs;
    private final ConcurrentHashMap<String, Channel> channelMap;

    public ServerChannelInitializer(ServerConfigs serverConfigs, ConcurrentHashMap<String, Channel> channelMap) {
        this.serverConfigs = serverConfigs;
        this.channelMap = channelMap;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        String ipAddr = ch.localAddress().toString().substring(1);
        ServerIPAddrConfig config = serverConfigs.get(ipAddr);
        selectCodec.select(config.getSerializeProtocol(), ch.pipeline());

        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                Request r = (Request)msg;
                String remoteAddr = ctx.channel().remoteAddress().toString().substring(1);
                if (!channelMap.containsKey(remoteAddr)) {
                    channelMap.put(remoteAddr, ctx.channel());
                }
                config.getRequestHandler().handleRequest(remoteAddr, ipAddr, r);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                super.exceptionCaught(ctx, cause);
                String remoteAddr = ctx.channel().remoteAddress().toString().substring(1);
                channelMap.remove(remoteAddr);
                ctx.close();
            }

            @Override
            public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                super.channelUnregistered(ctx);
                channelMap.remove(ctx.channel().remoteAddress().toString().substring(1));
                ctx.close();
            }
        });
    }
}
