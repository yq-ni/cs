package com.yq.cs.client.engine;

import com.yq.cs.message.struct.Response;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by nyq on 2017/4/1.
 */
public class MessageFuture {
    private Object result;
    private Object error;
    private CountDownLatch finish = new CountDownLatch(1);
    private OnReceiveListener listener;

    public MessageFuture() {

    }

    public MessageFuture(OnReceiveListener listener) {
        this.listener = listener;
    }

    public Object get() throws InterruptedException {
        finish.await();
        return result;
    }

    public void done(Response response) {
        this.result = response.getResult();
        this.error = response.getError();
        if (listener != null) {
            if (this.result != null)
                listener.onSuccess(this.result);
            else
                listener.onFail(this.error);
        }
        finish.countDown();
    }


}
