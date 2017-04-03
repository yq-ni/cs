package com.yq.cs.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by nyq on 2017/4/1.
 */
public class CSServiceParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String interfaceName = element.getAttribute("interfaceName");
        String ref = element.getAttribute("ref");
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(CSService.class);
        rootBeanDefinition.setLazyInit(false);
        rootBeanDefinition.getPropertyValues().addPropertyValue("interfaceName", interfaceName);
        rootBeanDefinition.getPropertyValues().addPropertyValue("ref", ref);
        parserContext.getRegistry().registerBeanDefinition(interfaceName, rootBeanDefinition);
        return rootBeanDefinition;
    }
}
