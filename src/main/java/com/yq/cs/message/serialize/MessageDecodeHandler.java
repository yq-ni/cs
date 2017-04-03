package com.yq.cs.message.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by nyq on 2017/4/3.
 */
public class MessageDecodeHandler extends ByteToMessageDecoder{
    private DecodeMessage decodeMessage;

    public MessageDecodeHandler(DecodeMessage decodeMessage) {
        this.decodeMessage = decodeMessage;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int messageLen = in.readInt();
        if (messageLen < 0) {
            ctx.close();
        }
        if (in.readableBytes() < messageLen) {
            in.resetReaderIndex();
            return;
        }
        byte[] messageByte = new byte[messageLen];
        in.readBytes(messageByte);
        Object msg = decodeMessage.decode(messageByte);
        out.add(msg);
    }
}
