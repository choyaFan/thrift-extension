package com.choy.thriftplus.core.retry;

public interface TRetryJob<Result, Error> {
    void runAsync(TCallBack<Result, Error> callBack);

    void onRetry(TRetry<Result, Error> retry);
}
