package com.choy.thriftplus.core.retry;

public class TRetryFailedException extends Exception {
    public TRetryFailedException(String msg){super(msg);}

    public TRetryFailedException(String msg, Throwable cause){super(msg, cause);}
}
