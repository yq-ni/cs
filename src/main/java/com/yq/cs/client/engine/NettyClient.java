package com.yq.cs.client.engine;

import com.yq.cs.client.RemoteServices;
import com.yq.cs.client.ServiceProperty;
import com.yq.cs.message.serialize.SelectCodec;
import com.yq.cs.message.struct.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nyq on 2017/4/2.
 */
public class NettyClient implements ClientEngine {
    private Bootstrap bootstrap;
    private NioEventLoopGroup group;
    private final HashMap<String, ChannelWrapper> channelWrapperMap;
    private final ConcurrentHashMap<String, MessageFuture> messageFutureHashMap;

    public NettyClient() {
        this.channelWrapperMap = new HashMap<>();
        this.messageFutureHashMap = new ConcurrentHashMap<>();
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer(this.messageFutureHashMap, this.channelWrapperMap));
    }

    public void connect(String ipAddr) throws Exception {
        ChannelWrapper channelWrapper = channelWrapperMap.get(ipAddr);
        if (channelWrapper == null) {
            synchronized (channelWrapperMap) {
                if (channelWrapperMap.get(ipAddr) == null) {
                    ChannelWrapper c = new ChannelWrapper();
                    channelWrapperMap.put(ipAddr, c);
                    channelWrapper = c;
                }
            }
        }
        if (!channelWrapper.isConnect()) {
            synchronized (channelWrapper) {
                if (!channelWrapper.isConnect()) {
                    String[] host_port = ipAddr.split(":");
                    ChannelFuture f = bootstrap.connect(host_port[0], Integer.valueOf(host_port[1])).sync();
                    new SelectCodec().select(RemoteServices.getIPAddrConfigMap().get(ipAddr).getProtocol(), f.channel().pipeline());
                    channelWrapper.setConnect(true);
                    channelWrapper.setChannelFuture(f);
                }
            }
        }
    }

    @Override
    public boolean send(String ipAddr, Request request, OnReceiveListener listener) {
        ChannelWrapper channelWrapper = channelWrapperMap.get(ipAddr);
        if (!channelWrapper.isConnect()) {
            try {
                connect(ipAddr);
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        MessageFuture messageFuture = new MessageFuture(listener);
        messageFutureHashMap.put(request.getMessageId(), messageFuture);
        channelWrapper.getChannelFuture().channel().writeAndFlush(request);
        return true;
    }


    @Override
    public Object getResult(String messageId) throws InterruptedException {
        MessageFuture messageFuture = messageFutureHashMap.get(messageId);
        if (messageFuture == null) return null;
        Object result = messageFuture.get();
        messageFutureHashMap.remove(messageId);
        return result;
    }

    @Override
    public void update(ServiceProperty serviceProperty) {
        synchronized (this) {
            if (!channelWrapperMap.containsKey(serviceProperty.getIpAddr())) {
                channelWrapperMap.put(serviceProperty.getIpAddr(), new ChannelWrapper());
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
        messageFutureHashMap.clear();
        channelWrapperMap.forEach((ipAddr, channelWrapper)->{
            channelWrapper.setConnect(false);
        });
    }
}
