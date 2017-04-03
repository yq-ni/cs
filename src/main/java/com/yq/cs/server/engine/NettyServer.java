package com.yq.cs.server.engine;


import com.yq.cs.server.ServerIPAddrConfig;
import com.yq.cs.server.scheduling.TaskThreadPool;
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
    private static final HashMap<String, ServerIPAddrConfig> IPAddrConfigMap = new HashMap<>();
    private static final ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    private TaskThreadPool taskThreadPool;

    public NettyServer(TaskThreadPool taskThreadPool) {
        this.taskThreadPool = taskThreadPool;
    }

    public void start() throws InterruptedException {
        taskThreadPool.start();
        boss =  new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerChannelInitializer());

        for (String ipAddr : IPAddrConfigMap.keySet()) {
            String[] host_port = ipAddr.split(":");
            serverBootstrap.bind(host_port[0], Integer.valueOf(host_port[1])).sync();
        }
    }

    public void stop() {
        taskThreadPool.stop();
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    public static HashMap<String, ServerIPAddrConfig> getIPAddrConfigMap() {
        return IPAddrConfigMap;
    }

    public static ConcurrentHashMap<String, Channel> getChannelMap() {
        return channelMap;
    }
}
