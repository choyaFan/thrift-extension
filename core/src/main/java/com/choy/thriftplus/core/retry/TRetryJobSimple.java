package com.choy.thriftplus.core.retry;

public abstract class TRetryJobSimple<Result, Error> implements TRetryJob<Result, Error>{
    protected TRetry<Result, Error> retry;
    
    public TRetryJobSimple(){}
    
    public TRetryJobSimple(TRetry<Result, Error> retry){
        this.retry = retry;
    }
    
    @Override
    public void onRetry(TRetry<Result, Error> retry){
        
    }
    
    public TRetry<Result, Error> getRetry(){
        return retry;
    }
}
