package com.yq.cs.server.scheduling;

import com.yq.cs.message.struct.Request;
import com.yq.cs.server.handlers.ResultHandler;
import com.yq.cs.server.servicehandlers.ServiceHandlersMap;

import java.lang.reflect.Method;

/**
 * Created by nyq on 2017/4/1.
 */
public class Task implements Runnable {

    private String remoteAddr;
    private Request request;
    private ResultHandler resultHandler;

    public Task(String remoteAddr, Request request, ResultHandler resultHandler) {
        this.remoteAddr = remoteAddr;
        this.request = request;
        this.resultHandler = resultHandler;
    }

    @Override
    public void run() {
        try {
            Object result = handle();
            resultHandler.handleResult(remoteAddr, request.getMessageId(), result, null);
        }
        catch (Exception e) {
            resultHandler.handleResult(remoteAddr, request.getMessageId(), null, e);
        }
    }

    public Object handle() throws Exception {
        Object handler = ServiceHandlersMap.getServiceHandlers().get(request.getClassName());
        Method method = handler.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
        return method.invoke(handler, request.getParameters());
    }
}
