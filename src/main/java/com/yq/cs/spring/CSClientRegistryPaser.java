package com.yq.cs.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by nyq on 2017/4/2.
 */
public class CSClientRegistryPaser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = element.getAttribute("id");
        String ipAddr = element.getAttribute("ipAddr");
        String protocol = element.getAttribute("protocol");
        boolean isLazy = Boolean.valueOf(element.getAttribute("isLazy"));

        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(CSClientRegistry.class);
        rootBeanDefinition.getPropertyValues().addPropertyValue("ipAddr", ipAddr);
        rootBeanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
        rootBeanDefinition.getPropertyValues().addPropertyValue("isLazy", isLazy);
        parserContext.getRegistry().registerBeanDefinition(id, rootBeanDefinition);
        return rootBeanDefinition;
    }
}
