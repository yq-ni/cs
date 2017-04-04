package com.yq.cs.spring;

import com.yq.cs.client.RemoteServices;
import com.yq.cs.client.config.ServiceProperty;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by nyq on 2017/4/1.
 */
public class CSReference implements ApplicationContextAware, FactoryBean {
    private String interfaceName;
    private String ipAddr;

    private Class<?> clazz;
    private Object proxy;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            this.clazz = this.getClass().getClassLoader().loadClass(interfaceName);

            ServiceProperty s =  new ServiceProperty();
            s.setIpAddr(ipAddr);
            s.setInterface(clazz);
            RemoteServices r = applicationContext.getBean(RemoteServices.class);
            r.registerService(s);

            proxy = r.getProxy(clazz);

        }
        catch (Exception e) {

        }
    }

    @Override
    public Object getObject() throws Exception {
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}
