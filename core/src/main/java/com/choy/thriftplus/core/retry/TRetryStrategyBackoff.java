package com.choy.thriftplus.core.retry;

import java.io.IOException;

public class TRetryStrategyBackoff implements BackOff, TRetryStrategy {
    public static final String NAME = "backoff";
    
    public static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
    
    public static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5;
    
    public static final double DEFAULT_MULTIPLIER = 1.5;
    
    public static final int DEFAULT_MAX_INTERVAL_MILLIS = 60000;
    
    public static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;
    
    public static final int DEFAULT_MAX_ATTEMPTS = -1;
    protected static final String FIELD_INITIAL_INTERVAL_MILLIS = "initialMillis";
    protected static final String FIELD_RANDOMIZATION_FACTOR = "randFact";
    protected static final String FIELD_MULTIPLIER = "mult";
    protected static final String FIELD_MAX_INTERVAL_MILLIS = "maxIntMillis";
    protected static final String FIELD_MAX_ELAPSED_TIME_MILLIS = "maxElapsedMillis";
    protected static final String FIELD_MAX_ATTEMPTS = "maxAttempts";
    
    private final int initialIntervalMillis;
    
    //A randomization factor of 0.5 results in a random period ranging between 50% below and 50% above the retry interval.
    private final double randomizationFactor;
    
    //value to multiply the current interval with for each retry attempt.
    private final double multiplier;
    
    //The maximum value of the back off period in milliseconds. Once the retry interval reaches this value it stops increasing.
    private final int maxIntervalMillis;
    
    //maximum value of attempts before failing.
    private final int maxAttempts;
    
    private final int maxElapsedTimeMillis;
    
    private int currentIntervalMillis;
    
    private int currentAttempts = 0;
    
    //system time in nanoseconds.
    private long startTimeNanos;
    
    //Creates an instance of ExponentialBackOffPolicy using default values.
    public TRetryStrategyBackoff() {
        this(new Builder());
    }
    
    protected TRetryStrategyBackoff(Builder builder) {
        initialIntervalMillis = builder.initialIntervalMillis;
        randomizationFactor = builder.randomizationFactor;
        multiplier = builder.multiplier;
        maxIntervalMillis = builder.maxIntervalMillis;
        maxElapsedTimeMillis = builder.maxElapsedTimeMillis;
        maxAttempts = builder.maxAttempts;
        if (initialIntervalMillis <= 0
                || (0 > randomizationFactor || randomizationFactor >= 1)
                || multiplier < 1
                || maxIntervalMillis < initialIntervalMillis
                || maxElapsedTimeMillis <= 0) {
            throw new IllegalArgumentException("Invalid input arguments");
        }
        reset();
    }
    
    static int getRandomValueFromInterval(
            double randomizationFactor, double random, int currentIntervalMillis) {
        double delta = randomizationFactor * currentIntervalMillis;
        double minInterval = currentIntervalMillis - delta;
        double maxInterval = currentIntervalMillis + delta;
        // Get a random value from the range [minInterval, maxInterval].
        // The formula used below has a +1 because if the minInterval is 1 and the maxInterval is 3 then
        // we want a 33% chance for selecting either 1, 2 or 3.
        int randomValue = (int) (minInterval + (random * (maxInterval - minInterval + 1)));
        return randomValue;
    }
    
    public long nextBackOffMillis() throws IOException {
        return nextBackOffMillis(true);
    }

    public long nextBackOffMillis(boolean inc) throws IOException {
        // Make sure we have not gone over the maximum elapsed time.
        if (getElapsedTimeMillis() > maxElapsedTimeMillis) {
            return STOP;
        }
        int randomizedInterval =
                getRandomValueFromInterval(randomizationFactor, Math.random(), currentIntervalMillis);

        if (inc) {
            incrementCurrentInterval();
        }

        return randomizedInterval;
    }

    
    public final int getInitialIntervalMillis() {
        return initialIntervalMillis;
    }
    
    public final double getRandomizationFactor() {
        return randomizationFactor;
    }
    
    public final int getCurrentIntervalMillis() {
        return currentIntervalMillis;
    }
    
    public final double getMultiplier() {
        return multiplier;
    }
    
    public final int getMaxIntervalMillis() {
        return maxIntervalMillis;
    }
    
    public final int getMaxAttempts() {
        return maxAttempts;
    }
    
    public final int getMaxElapsedTimeMillis() {
        return maxElapsedTimeMillis;
    }
    
    public final long getElapsedTimeMillis() {
        return (System.nanoTime() - startTimeNanos) / 1000000;
    }
    
    private void incrementCurrentInterval() {
        // Check for overflow, if overflow is detected set the current interval to the max interval.
        if (currentIntervalMillis >= maxIntervalMillis / multiplier) {
            currentIntervalMillis = maxIntervalMillis;
        } else {
            currentIntervalMillis *= multiplier;
        }
    }

    @Override
    public String getName() {
        return "backoff";
    }

    @Override
    public void onFail() {
        currentAttempts += 1;
        incrementCurrentInterval();
    }

    @Override
    public void onSuccess() {

    }
    
    public final void reset() {
        currentIntervalMillis = initialIntervalMillis;
        currentAttempts = 0;
        startTimeNanos = System.nanoTime();
    }

    @Override
    public boolean shouldContinue() {
        if (getElapsedTimeMillis() > maxElapsedTimeMillis) {
            return false;
        }

        return maxAttempts < 0 || currentAttempts < maxAttempts;
    }

    @Override
    public long getWaitMilli() {
        try {
            return nextBackOffMillis(false);
        } catch (IOException e) {
            return STOP;
        }
    }

    @Override
    public TRetryStrategy copy() {
        return new TRetryStrategyBackoff.Builder()
                .setInitialIntervalMillis(getInitialIntervalMillis())
                .setMaxAttempts(getMaxAttempts())
                .setMaxElapsedTimeMillis(getMaxElapsedTimeMillis())
                .setMaxIntervalMillis(getMaxIntervalMillis())
                .setMultiplier(getMultiplier())
                .setRandomizationFactor(getRandomizationFactor())
                .build();
    }
    
    public static class Builder {
        
        int initialIntervalMillis = DEFAULT_INITIAL_INTERVAL_MILLIS;
        
        double randomizationFactor = DEFAULT_RANDOMIZATION_FACTOR;
        
        double multiplier = DEFAULT_MULTIPLIER;
        
        int maxIntervalMillis = DEFAULT_MAX_INTERVAL_MILLIS;
        
        int maxAttempts = DEFAULT_MAX_ATTEMPTS;

        int maxElapsedTimeMillis = DEFAULT_MAX_ELAPSED_TIME_MILLIS;

        public Builder() {
        }
        
        public TRetryStrategyBackoff build() {
            return new TRetryStrategyBackoff(this);
        }
        
        public final int getInitialIntervalMillis() {
            return initialIntervalMillis;
        }
        
        public Builder setInitialIntervalMillis(int initialIntervalMillis) {
            this.initialIntervalMillis = initialIntervalMillis;
            return this;
        }
        
        public final double getRandomizationFactor() {
            return randomizationFactor;
        }
        
        public Builder setRandomizationFactor(double randomizationFactor) {
            this.randomizationFactor = randomizationFactor;
            return this;
        }
        
        public final double getMultiplier() {
            return multiplier;
        }
        
        public Builder setMultiplier(double multiplier) {
            this.multiplier = multiplier;
            return this;
        }
        
        public final int getMaxIntervalMillis() {
            return maxIntervalMillis;
        }
        
        public Builder setMaxIntervalMillis(int maxIntervalMillis) {
            this.maxIntervalMillis = maxIntervalMillis;
            return this;
        }
        
        public final int getMaxElapsedTimeMillis() {
            return maxElapsedTimeMillis;
        }
        
        public Builder setMaxElapsedTimeMillis(int maxElapsedTimeMillis) {
            this.maxElapsedTimeMillis = maxElapsedTimeMillis;
            return this;
        }
        
        public int getMaxAttempts() {
            return this.maxAttempts;
        }
        
        public Builder setMaxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
            return this;
        }
    }
}

