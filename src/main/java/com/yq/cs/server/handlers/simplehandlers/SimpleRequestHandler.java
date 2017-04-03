package com.yq.cs.server.handlers.simplehandlers;

import com.yq.cs.message.struct.Request;
import com.yq.cs.server.handlers.RequestHandler;
import com.yq.cs.server.scheduling.TaskThreadPool;

/**
 * Created by nyq on 2017/4/2.
 */
public class SimpleRequestHandler implements RequestHandler {
    private TaskThreadPool taskThreadPool;

    public SimpleRequestHandler(TaskThreadPool taskThreadPool) {
        this.taskThreadPool = taskThreadPool;
    }

    @Override
    public void handleRequest(String remoteAddr, String localAddr, Request request) {
        taskThreadPool.handle(remoteAddr, localAddr, request);
    }
}
