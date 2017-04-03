package com.yq.cs.message.serialize;

import com.yq.cs.message.SerializeProtocol;
import com.yq.cs.message.serialize.jdk.JDKDecoder;
import com.yq.cs.message.serialize.jdk.JDKEncoder;
import com.yq.cs.message.serialize.kryo.KryoDecoder;
import com.yq.cs.message.serialize.kryo.KryoEncoder;
import io.netty.channel.ChannelPipeline;

/**
 * Created by nyq on 2017/4/2.
 */
public class SelectCodec {
    public void select(SerializeProtocol serializeProtocol, ChannelPipeline pipeline) {
        DecodeMessage decodeMessage;
        EncodeMessage encodeMessage;
        switch (serializeProtocol) {
            case KRYO: {
                decodeMessage = new KryoDecoder();
                encodeMessage = new KryoEncoder();
                break;
            }
            case JDK: default: {
                decodeMessage = new JDKDecoder();
                encodeMessage = new JDKEncoder();
                break;
            }
        }
        pipeline.addFirst(new MessageDecodeHandler(decodeMessage));
        pipeline.addFirst(new MessageEncodeHandler(encodeMessage));
    }
}
