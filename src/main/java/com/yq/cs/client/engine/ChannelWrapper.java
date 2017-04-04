package com.yq.cs.client.engine;

import com.yq.cs.client.config.ClientIPAddrConfig;
import io.netty.channel.ChannelFuture;

/**
 * Created by nyq on 2017/4/1.
 */
public class ChannelWrapper {
    private ChannelFuture channelFuture;
    private boolean isConnect;
    private ClientIPAddrConfig config;

    public ChannelWrapper() {}

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public ClientIPAddrConfig getConfig() {
        return config;
    }

    public void setConfig(ClientIPAddrConfig config) {
        this.config = config;
    }
}
