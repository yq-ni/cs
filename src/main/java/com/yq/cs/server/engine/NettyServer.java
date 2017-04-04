package com.yq.cs.server.engine;


import com.yq.cs.server.config.ServerConfigs;
import com.yq.cs.server.config.ServerIPAddrConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nyq on 2017/3/31.
 */
public class NettyServer {

    private NioEventLoopGroup worker;
    private NioEventLoopGroup boss;
    private final ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();
    private ServerConfigs serverConfigs;

    public NettyServer(ServerConfigs serverConfigs) {
        this.serverConfigs = serverConfigs;
    }

    public void start() throws InterruptedException {
        boss =  new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerChannelInitializer(serverConfigs, channelMap));

        for (String ipAddr : serverConfigs.getAllIPAddrs()) {
            String[] host_port = ipAddr.split(":");
            serverBootstrap.bind(host_port[0], Integer.valueOf(host_port[1])).sync();
        }
    }

    public void stop() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    public ConcurrentHashMap<String, Channel> getChannelMap() {
        return channelMap;
    }
}
