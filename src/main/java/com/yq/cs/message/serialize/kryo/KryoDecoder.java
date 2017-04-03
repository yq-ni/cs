package com.yq.cs.message.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.yq.cs.message.serialize.DecodeMessage;

/**
 * Created by nyq on 2017/4/3.
 */
public class KryoDecoder implements DecodeMessage {

    public KryoDecoder() {}

    @Override
    public Object decode(byte[] bytes) throws Exception {
        Input input = new Input(bytes);
        Kryo kryo = KryoPoolFactory.getKryoPoolFactory().getPool().borrow();
        Object o = kryo.readClassAndObject(input);
        KryoPoolFactory.getKryoPoolFactory().getPool().release(kryo);
        return o;
    }
}
