package com.yq.cs.server.servicehandlers;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nyq on 2017/4/1.
 */
public class ServiceHandlersMap {
    private ConcurrentHashMap<String, Object> handlersMap;

    private ServiceHandlersMap() {
        handlersMap = new ConcurrentHashMap<>();
    }

    public void put(String name, Object handler) {
        handlersMap.put(name, handler);
    }

    public Object get(String name) {
        return handlersMap.get(name);
    }

    public static ServiceHandlersMap getServiceHandlers() {
        return ServiceHandlersHolder.HOLDER;
    }

    private static class ServiceHandlersHolder {
        private static final ServiceHandlersMap HOLDER = new ServiceHandlersMap();
    }
}
