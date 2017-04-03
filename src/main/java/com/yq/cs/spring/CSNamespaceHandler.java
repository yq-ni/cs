package com.yq.cs.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by nyq on 2017/4/1.
 */
public class CSNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("service", new CSServiceParser());
        registerBeanDefinitionParser("reference", new CSReferenceParser());
        registerBeanDefinitionParser("registry", new CSRegistryParser());
        registerBeanDefinitionParser("clientRegistry", new CSClientRegistryPaser());
    }
}