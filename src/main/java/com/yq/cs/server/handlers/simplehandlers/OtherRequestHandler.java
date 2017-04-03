package com.yq.cs.server.handlers.simplehandlers;

import com.yq.cs.message.struct.Request;
import com.yq.cs.server.handlers.RequestHandler;

/**
 * Created by nyq on 2017/4/3.
 */
public class OtherRequestHandler implements RequestHandler {
    @Override
    public void handleRequest(String remoteAddr, String localAddr, Request request) {
        System.out.println(remoteAddr+localAddr+request);
    }
}
