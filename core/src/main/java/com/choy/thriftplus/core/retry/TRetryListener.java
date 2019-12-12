package com.choy.thriftplus.core.retry;

public interface TRetryListener<Result, Error> {
    void onSuccess(Result result, TRetry<Result, Error> retry);

    void onFail(TRetryJobErr<Error> error);
}
