package com.yq.cs.client.serviceproxy;

import com.yq.cs.client.engine.ClientEngine;
import com.yq.cs.client.util.Utils;
import com.yq.cs.message.struct.Request;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by nyq on 2017/4/1.
 */
public class ServiceProxy<T> implements InvocationHandler{
    private Class<T> clazz;
    private ClientEngine clientEngine;
    private String ipAddr;

    public ServiceProxy(Class<T> clazz, String ipAddr,  ClientEngine clientEngine) {
        this.clazz = clazz;
        this.ipAddr = ipAddr;
        this.clientEngine = clientEngine;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request r = Utils.buildRequest(clazz.getName(), method.getName(), method.getParameterTypes(), args);
        boolean success = clientEngine.send(ipAddr, r, null);
        if (!success) {
            return Utils.isBasicType(method.getReturnType()) ? -1 : null;
        }
        return clientEngine.getResult(r.getMessageId());
    }
}
