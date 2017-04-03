package com.yq.cs.message.serialize.jdk;

import com.yq.cs.message.serialize.DecodeMessage;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.serialization.ClassResolvers;

import java.io.ObjectInputStream;

/**
 * Created by nyq on 2017/4/3.
 */
public class JDKDecoder implements DecodeMessage{

    @Override
    public Object decode(byte[] bytes) throws Exception{
        ObjectInputStream is = new CompactObjectInputStream(new ByteBufInputStream(Unpooled.copiedBuffer(bytes)), ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader()));
        Object result = is.readObject();
        is.close();
        return result;

    }
}
