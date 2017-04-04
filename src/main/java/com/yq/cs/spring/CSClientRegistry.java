package com.yq.cs.spring;

import com.yq.cs.client.config.ClientIPAddrConfig;
import com.yq.cs.client.RemoteServices;
import com.yq.cs.message.SerializeProtocol;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by nyq on 2017/4/2.
 */
public class CSClientRegistry implements ApplicationContextAware, ApplicationListener, DisposableBean {
    private String ipAddr;
    private String protocol;
    private boolean isLazy;

    private ApplicationContext ctx;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        ClientIPAddrConfig config = new ClientIPAddrConfig();
        config.setIpAddr(ipAddr);
        config.setProtocol(Enum.valueOf(SerializeProtocol.class, protocol));
        config.setLazy(isLazy);
        ctx.getBean(RemoteServices.class).registerConfig(config);
        ctx.getBean(RemoteServices.class).connectNecessary();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        ctx.getBean(RemoteServices.class).destroy();
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

    public boolean getIsLazy() {
        return isLazy;
    }

    public void setIsLazy(boolean lazy) {
        isLazy = lazy;
    }
}
