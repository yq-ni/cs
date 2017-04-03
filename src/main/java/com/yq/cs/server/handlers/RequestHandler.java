package com.yq.cs.server.handlers;

import com.yq.cs.message.struct.Request;

import java.net.SocketAddress;

/**
 * Created by nyq on 2017/4/1.
 */
public interface RequestHandler {
    void handleRequest(String remoteAddr, String localAddr, Request request);
}
