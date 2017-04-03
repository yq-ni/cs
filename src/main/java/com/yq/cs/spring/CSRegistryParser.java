package com.yq.cs.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by nyq on 2017/4/2.
 */
public class CSRegistryParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = element.getAttribute("id");
        String protocol = element.getAttribute("protocol");
        String ipAddr = element.getAttribute("ipAddr");
        String requestHandler = element.getAttribute("requestHandlerRef");
        String resultHandler = element.getAttribute("resultHandlerRef");
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(CSRegistry.class);
        beanDefinition.setLazyInit(false);

        beanDefinition.getPropertyValues().addPropertyValue("ipAddr", ipAddr);
        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
        beanDefinition.getPropertyValues().addPropertyValue("requestHandlerRef", requestHandler);
        beanDefinition.getPropertyValues().addPropertyValue("resultHandlerRef", resultHandler);
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        return beanDefinition;
    }
}
