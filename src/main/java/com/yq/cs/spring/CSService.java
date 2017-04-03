package com.yq.cs.spring;

import com.yq.cs.server.servicehandlers.ServiceHandlersMap;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by nyq on 2017/4/1.
 */
public class CSService implements ApplicationContextAware, ApplicationListener {
    private String interfaceName;
    private String ref;

    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        ServiceHandlersMap.getServiceHandlers().put(interfaceName, ctx.getBean(ref));
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}
