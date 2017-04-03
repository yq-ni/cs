package com.yq.cs.message.struct;

import java.io.Serializable;

/**
 * Created by nyq on 2017/3/31.
 */
public class Response implements Serializable {
    private String messageId;
    private Object result;
    private Object error;

    public Response(){}

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return String.format("Response: [messageId=%s, result=%s, error=%s]", messageId, result, error);
    }
}
