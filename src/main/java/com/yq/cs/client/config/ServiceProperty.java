package com.yq.cs.client.config;

/**
 * Created by nyq on 2017/4/2.
 */
public class ServiceProperty {
    private Class<?> clazz;
    private String ipAddr;

    public ServiceProperty() {}

    public Class<?> getInterface() {
        return clazz;
    }

    public void setInterface(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

}
