package com.choy.thriftplus.core.retry;

public abstract class TRetryJonSimpleSafe<Result, Error> extends TRetryJobSimple<Result, Error>{
    @Override
    public void runAsync(TCallBack<Result, Error> callBack) {
        try {
            runAsyncNoException(callBack);
        }catch (Throwable throwable){
            callBack.onFail(new TRetryJobErr<Error>(throwable), true);
        }
    }
    
    public abstract void runAsyncNoException(TCallBack<Result, Error> callBack) throws Throwable;
}
