package com.yq.cs.message.struct;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by nyq on 2017/3/31.
 */
public class Request implements Serializable {
    private int code = 0x12340101;
    private final String messageId = UUID.randomUUID().toString();
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public Request(){}

    public String getMessageId() {
        return messageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return String.format("Request: [code=%d, messageId=%s, className=%s, methodName=%s]", code, messageId, className, methodName);
    }
}
