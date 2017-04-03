package com.yq.cs.client;

import com.yq.cs.client.engine.ClientEngine;
import com.yq.cs.client.engine.NettyClient;
import com.yq.cs.client.engine.OnReceiveListener;
import com.yq.cs.client.serviceproxy.ServiceProxy;
import com.yq.cs.client.util.Utils;
import com.yq.cs.message.struct.Request;

import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by nyq on 2017/4/1.
 */
public class RemoteServices {

    private ClientEngine clientEngine;

    private HashMap<String, Object> serviceProxyMap = new HashMap<>();

    private static HashMap<String, ClientIPAddrConfig> configHMap = new HashMap<>();

    private HashMap<String, ServiceProperty> servicePropertyMap = new HashMap<>();

    public RemoteServices(ClientEngine clientEngine) {
        this.clientEngine = clientEngine;
    }

    public RemoteServices() {
        this(new NettyClient());
    }

    @SuppressWarnings("unchecked")
    private  <T> T create(Class<T> serviceInterface) {
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class<?>[]{serviceInterface},
                new ServiceProxy(serviceInterface,
                        servicePropertyMap.get(serviceInterface.getName()).getIpAddr(),
                        clientEngine));
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> serviceInterface) {
        return (T) serviceProxyMap.get(serviceInterface.getName());
    }

    public void callAsync(OnReceiveListener r, Class<?> clazz, String methodName, Object...args) {
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i=0; i < args.length; i++) {
            parameterTypes[i] = Utils.getClassType(args[i]);
        }
        Request request = Utils.buildRequest(clazz.getName(), methodName, parameterTypes, args);
        String ipAddr = servicePropertyMap.get(clazz.getName()).getIpAddr();
        clientEngine.send(ipAddr, request, r);
    }

    public void registerService(ServiceProperty serviceProperty) {
        servicePropertyMap.put(serviceProperty.getInterface().getName(), serviceProperty);
        serviceProxyMap.put(serviceProperty.getInterface().getName(), create(serviceProperty.getInterface()));
        clientEngine.update(serviceProperty);
    }



    public void connectNecessary() {
        for (ClientIPAddrConfig config : configHMap.values()) {
            if (!config.isLazy()) {
                try {
                    clientEngine.connect(config.getIpAddr());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HashMap<String, ServiceProperty> getServicePropertyMap() {
        return servicePropertyMap;
    }

    public static HashMap<String, ClientIPAddrConfig> getIPAddrConfigMap() {
        return configHMap;
    }

    public void destroy() {
        clientEngine.stop();
    }

}
