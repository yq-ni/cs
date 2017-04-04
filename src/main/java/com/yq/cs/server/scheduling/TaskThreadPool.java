package com.yq.cs.server.scheduling;

import com.yq.cs.message.struct.Request;
import com.yq.cs.server.config.ServerConfigs;
import com.yq.cs.server.engine.NettyServer;

import java.net.SocketAddress;
import java.util.concurrent.*;

/**
 * Created by nyq on 2017/4/1.
 */
public class TaskThreadPool {
    private ExecutorService executorService;
    private ServerConfigs serverConfigs;

    public TaskThreadPool(ServerConfigs serverConfigs) {
        this.serverConfigs = serverConfigs;
        executorService = Executors.newCachedThreadPool();
    }

    public void stop() {
        executorService.shutdown();
    }

    public void handle(String remoteAddr, String localAddr, Request request) {
        executorService.submit(new Task(remoteAddr,
                request,
                serverConfigs.get(localAddr).getResultHandler()));
    }

}
