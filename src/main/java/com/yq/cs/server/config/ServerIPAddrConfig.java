package com.yq.cs.server.config;

import com.yq.cs.message.SerializeProtocol;
import com.yq.cs.server.handlers.RequestHandler;
import com.yq.cs.server.handlers.ResultHandler;

/**
 * Created by nyq on 2017/4/2.
 */
public class ServerIPAddrConfig {
    private String ipAddr;
    private SerializeProtocol serializeProtocol;
    private RequestHandler requestHandler;
    private ResultHandler resultHandler;

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public SerializeProtocol getSerializeProtocol() {
        return serializeProtocol;
    }

    public void setSerializeProtocol(SerializeProtocol serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public ResultHandler getResultHandler() {
        return resultHandler;
    }

    public void setResultHandler(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }
}
