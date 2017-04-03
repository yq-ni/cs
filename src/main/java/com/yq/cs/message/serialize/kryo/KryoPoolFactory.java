package com.yq.cs.message.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.yq.cs.message.struct.Request;
import com.yq.cs.message.struct.Response;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * Created by nyq on 2017/4/3.
 */
public class KryoPoolFactory {

    private KryoFactory factory = new KryoFactory() {
        public Kryo create() {
            Kryo kryo = new Kryo();
            kryo.setReferences(false);
            kryo.register(Request.class);
            kryo.register(Response.class);
            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };

    private KryoPool pool = new KryoPool.Builder(factory).build();

    private KryoPoolFactory() {
    }

    public static KryoPoolFactory getKryoPoolFactory() {
        return Holder.HOLDER;
    }

    public KryoPool getPool() {
        return pool;
    }

    private static final class Holder {
        private static KryoPoolFactory HOLDER = new KryoPoolFactory();
    }
}
