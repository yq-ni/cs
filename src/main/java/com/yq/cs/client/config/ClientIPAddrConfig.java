package com.yq.cs.client.config;

import com.yq.cs.message.SerializeProtocol;

/**
 * Created by nyq on 2017/4/2.
 */
public class ClientIPAddrConfig {
    private String ipAddr;
    private SerializeProtocol protocol;
    private boolean isLazy;

    public ClientIPAddrConfig() {}

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public SerializeProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(SerializeProtocol protocol) {
        this.protocol = protocol;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }
}
