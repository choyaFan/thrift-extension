package com.choy.thriftplus.core.retry;


//wrapper for the job error
public class TRetryJobErr<Error> {
    Error error;

    protected Throwable throwable;

    public TRetryJobErr(){}

    public TRetryJobErr(Error error){
        this.error = error;
    }

    public TRetryJobErr(Throwable throwable){
        this.throwable = throwable;
    }

    public TRetryJobErr(Error error, Throwable throwable){
        this.error = error;
        this.throwable = throwable;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
