package com.yq.cs.spring;

import com.yq.cs.message.SerializeProtocol;
import com.yq.cs.server.ServerIPAddrConfig;
import com.yq.cs.server.engine.NettyServer;
import com.yq.cs.server.handlers.RequestHandler;
import com.yq.cs.server.handlers.ResultHandler;
import com.yq.cs.server.handlers.simplehandlers.SimpleRequestHandler;
import com.yq.cs.server.handlers.simplehandlers.SimpleResultHandler;
import com.yq.cs.server.scheduling.TaskThreadPool;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by nyq on 2017/4/2.
 */
public class CSRegistry implements ApplicationContextAware {
    private String ipAddr;
    private String protocol;
    private String requestHandlerRef;
    private String resultHandlerRef;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ServerIPAddrConfig ipAddrConfig = new ServerIPAddrConfig();
        ipAddrConfig.setIpAddr(ipAddr);
        ipAddrConfig.setSerializeProtocol(Enum.valueOf(SerializeProtocol.class, protocol));
        if (requestHandlerRef != null && requestHandlerRef.length() != 0) {
            ipAddrConfig.setRequestHandler((RequestHandler) applicationContext.getBean(requestHandlerRef));
        }
        else {
            ipAddrConfig.setRequestHandler(new SimpleRequestHandler((TaskThreadPool) applicationContext.getBean("taskThreadPool")));
        }
        if (resultHandlerRef != null && resultHandlerRef.length() != 0) {
            ipAddrConfig.setResultHandler((ResultHandler) applicationContext.getBean(resultHandlerRef));
        }
        else {
            ipAddrConfig.setResultHandler(new SimpleResultHandler());
        }

        NettyServer.getIPAddrConfigMap().put(ipAddr, ipAddrConfig);
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRequestHandlerRef() {
        return requestHandlerRef;
    }

    public void setRequestHandlerRef(String requestHandlerRef) {
        this.requestHandlerRef = requestHandlerRef;
    }

    public String getResultHandlerRef() {
        return resultHandlerRef;
    }

    public void setResultHandlerRef(String resultHandlerRef) {
        this.resultHandlerRef = resultHandlerRef;
    }
}
