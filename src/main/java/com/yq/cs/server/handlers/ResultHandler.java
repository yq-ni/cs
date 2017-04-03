package com.yq.cs.server.handlers;

/**
 * Created by nyq on 2017/4/1.
 */
public interface ResultHandler {
    void handleResult(String remoteAddr, String messageId, Object result, Object error);
}
