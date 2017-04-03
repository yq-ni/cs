package com.yq.cs.message.serialize.jdk;

import com.yq.cs.message.serialize.EncodeMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;

import java.io.ObjectOutputStream;

/**
 * Created by nyq on 2017/4/3.
 */
public class JDKEncoder implements EncodeMessage {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    @Override
    public void encode(ByteBuf out, Object message) throws Exception {
        if (message == null) {
            return;
        }
        int startIndex = out.writerIndex();
        ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(out);
        byteBufOutputStream.write(LENGTH_PLACEHOLDER);
        ObjectOutputStream objectOutputStream = new CompactObjectOutputStream(byteBufOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        objectOutputStream.close();

        int endIndex = out.writerIndex();
        out.setInt(startIndex, endIndex - startIndex - 4);
    }
}
