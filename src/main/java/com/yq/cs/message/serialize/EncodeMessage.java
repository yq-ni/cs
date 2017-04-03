package com.yq.cs.message.serialize;

import io.netty.buffer.ByteBuf;

/**
 * Created by nyq on 2017/4/3.
 */
public interface EncodeMessage {
    void encode(final ByteBuf out, Object message) throws Exception;
}
