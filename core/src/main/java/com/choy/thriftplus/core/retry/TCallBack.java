package com.choy.thriftplus.core.retry;

public interface TCallBack<Result, Error> {
    void onSuccess(Result result);

    void onFail(TRetryJobErr<Error> error, Boolean abort);
}
