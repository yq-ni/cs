package com.yq.cs.server.handlers.simplehandlers;

import com.yq.cs.message.struct.Response;
import com.yq.cs.server.engine.NettyServer;
import com.yq.cs.server.handlers.ResultHandler;
import io.netty.channel.Channel;

/**
 * Created by nyq on 2017/4/2.
 */
public class SimpleResultHandler implements ResultHandler {
    @Override
    public void handleResult(String remoteAddr, String messageId, Object result, Object error) {
        Response response = new Response();
        response.setMessageId(messageId);
        response.setResult(result);
        response.setError(error);
        Channel channel = NettyServer.getChannelMap().get(remoteAddr);

        if (channel != null) {
            channel.writeAndFlush(response);
        }
        else {
            // TODO: 2017/4/3
        }
    }
}
