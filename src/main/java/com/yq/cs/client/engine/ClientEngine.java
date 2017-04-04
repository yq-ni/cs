package com.yq.cs.client.engine;

import com.yq.cs.client.ClientIPAddrConfig;
import com.yq.cs.client.ServiceProperty;
import com.yq.cs.message.struct.Request;

/**
 * Created by nyq on 2017/4/2.
 */
public interface ClientEngine {
    void connect(String ipAddr) throws Exception;
    boolean send(String ipAddr, Request request, OnReceiveListener listener);
    Object getResult(String messageId) throws Exception;
    void registerConfig(ClientIPAddrConfig config);
    void stop();
}
