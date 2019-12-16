package com.choy.thriftplus.core.retry;

public interface TFuture<Result, Error> {
    //return true if async task is still running
    boolean isRunning();
    
    //return true if async task finished its execution
    boolean isDone();
    
    //triggers cancellation of the task
    //disables invocation of the next attempt and doesn't interrupt current running job
    void cancel();
    
    //skip the backoff waiting internal
    void runNow();
}
