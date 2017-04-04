package com.yq.cs.server.config;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by nyq on 2017/4/4.
 */
public class ServerConfigs {
    private HashMap<String, ServerIPAddrConfig> configMap;

    public ServerConfigs() {
        configMap = new HashMap<>();
    }

    public void put(String ipAddr, ServerIPAddrConfig config) {
        configMap.put(ipAddr, config);
    }

    public ServerIPAddrConfig get(String ipAddr) {
        return configMap.get(ipAddr);
    }

    public Set<String> getAllIPAddrs() {
        return configMap.keySet();
    }
}
