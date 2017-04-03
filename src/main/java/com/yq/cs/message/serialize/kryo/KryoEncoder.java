package com.yq.cs.message.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.yq.cs.message.serialize.EncodeMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;

import java.io.ByteArrayOutputStream;

/**
 * Created by nyq on 2017/4/3.
 */
public class KryoEncoder implements EncodeMessage {

    public KryoEncoder() {}
    @Override
    public void encode(ByteBuf out, Object message) throws Exception {
        if (message == null) return;
        int startIndex = out.writerIndex();
        out.writeBytes(new byte[4]);
        Output output = new Output(new ByteBufOutputStream(out));
        Kryo kryo = KryoPoolFactory.getKryoPoolFactory().getPool().borrow();
        kryo.writeClassAndObject(output, message);
        output.flush();
        output.close();
        KryoPoolFactory.getKryoPoolFactory().getPool().release(kryo);

        out.setInt(startIndex, out.writerIndex()-startIndex-4);
    }
}
