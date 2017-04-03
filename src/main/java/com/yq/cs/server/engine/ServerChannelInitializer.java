package com.yq.cs.server.engine;

import com.yq.cs.message.serialize.SelectCodec;
import com.yq.cs.message.struct.Request;

import com.yq.cs.server.ServerIPAddrConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by nyq on 2017/3/31.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private SelectCodec selectCodec = new SelectCodec();
    public ServerChannelInitializer() {}

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        String ipAddr = ch.localAddress().toString().substring(1);
        ServerIPAddrConfig config = NettyServer.getIPAddrConfigMap().get(ipAddr);
        selectCodec.select(config.getSerializeProtocol(), ch.pipeline());

        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                Request r = (Request)msg;
                String remoteAddr = ctx.channel().remoteAddress().toString().substring(1);
                if (!NettyServer.getChannelMap().contains(remoteAddr)) {
                    NettyServer.getChannelMap().put(remoteAddr, ctx.channel());
                }
                config.getRequestHandler().handleRequest(remoteAddr, ipAddr, r);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                super.exceptionCaught(ctx, cause);
                String remoteAddr = ctx.channel().remoteAddress().toString().substring(1);
                NettyServer.getChannelMap().remove(remoteAddr);
                ctx.close();
            }

            @Override
            public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                super.channelUnregistered(ctx);
                NettyServer.getChannelMap().remove(ctx.channel().remoteAddress().toString().substring(1));
                ctx.close();
            }
        });
    }
}
