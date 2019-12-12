package com.choy.thriftplus.core.retry;

public class TRetryFailedException extends Exception {
    TRetryFailedException(String msg){super(msg);}

    TRetryFailedException(String msg, Throwable cause){super(msg, cause);}
}
