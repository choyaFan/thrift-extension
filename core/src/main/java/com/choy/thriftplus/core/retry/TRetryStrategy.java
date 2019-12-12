package com.choy.thriftplus.core.retry;

public interface TRetryStrategy {
    String getName();

    void onFail();
    void onSuccess();
    void reset();
    boolean shouldContinue();
    long getWaitMilli();

    TRetryStrategy copy();
//    JSONObject toJSON(JSONObject json);
}
