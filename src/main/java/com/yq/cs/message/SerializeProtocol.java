package com.yq.cs.message;

/**
 * Created by nyq on 2017/4/2.
 */
public enum SerializeProtocol {
    JDK("JDK"), KRYO("KRYO");
    private String serializeProtocol;
    SerializeProtocol(String serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }
    String getSerializeProtocol() {
        return serializeProtocol;
    }
}
