package com.yq.cs.message.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by nyq on 2017/4/3.
 */
public class MessageEncodeHandler extends MessageToByteEncoder{
    private EncodeMessage encodeMessage;

    public MessageEncodeHandler(EncodeMessage encodeMessage) {
        this.encodeMessage = encodeMessage;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        encodeMessage.encode(out, msg);
    }
}
