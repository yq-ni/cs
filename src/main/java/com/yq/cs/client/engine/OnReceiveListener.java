package com.yq.cs.client.engine;

/**
 * Created by nyq on 2017/4/3.
 */
public interface OnReceiveListener {
    void onSuccess(Object result);
    void onFail(Object error);
}
