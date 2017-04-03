package com.yq.cs.server.scheduling;

import com.yq.cs.message.struct.Request;
import com.yq.cs.server.engine.NettyServer;

import java.net.SocketAddress;
import java.util.concurrent.*;

/**
 * Created by nyq on 2017/4/1.
 */
public class TaskThreadPool {
    private ExecutorService executorService;

    public TaskThreadPool() {}

    public void start() {
        executorService = Executors.newCachedThreadPool();
    }

    public void stop() {
        executorService.shutdown();
    }

    public void handle(String remoteAddr, String localAddr, Request request) {
        executorService.submit(new Task(remoteAddr,
                request,
                NettyServer.getIPAddrConfigMap().get(localAddr).getResultHandler()));
    }

}
