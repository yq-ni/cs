package com.yq.cs.message.serialize;

/**
 * Created by nyq on 2017/4/3.
 */
public interface DecodeMessage {
    Object decode(byte[] bytes) throws Exception;
}
