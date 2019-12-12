package com.choy.thriftplus.core.retry;

public class TRetryStrategyImpl implements TRetryStrategy{
    public static final String NAME = "simple";
    protected static final String FIELD_MAX_ATTEMPTS = "maxAttempts";

    /**
     * Indicates that no more retries should be made for use in {@link #getWaitMilli()}.
     */
    static final long STOP = -1L;
    static final long NOWAIT = 0L;
    protected int maxAttempts;
    protected int attempts = 0;

    public TRetryStrategyImpl(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public TRetryStrategyImpl() {
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onFail() {
        attempts += 1;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void reset() {
        attempts = 0;
    }

    @Override
    public boolean shouldContinue() {
        return maxAttempts < 0 || attempts < maxAttempts;
    }

    @Override
    public long getWaitMilli() {
        return NOWAIT;
    }

    @Override
    public TRetryStrategy copy() {
        return new TRetryStrategyImpl(maxAttempts);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        TRetryStrategyImpl that = (TRetryStrategyImpl) obj;
        return that.maxAttempts == maxAttempts;
    }
}
