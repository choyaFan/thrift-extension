package com.choy.thriftplus.core.retry;

import java.util.LinkedList;
import java.util.List;

public class TRetry<Result, Error> implements TCallBack<Result, Error> {
    protected static final String FIELD_STRATEGY_TYPE = "strategy";
    protected static final String FIELD_STRATEGY_DATA = "strategyConf";

    // Current number of attempts
    protected int attempts = 0;

    // Retry strategy to use.
    protected TRetryStrategy retryStrategy = new TRetryStrategyImpl(0);

    // All listeners for async runs
    protected final List<TRetryListener<Result, Error>> listeners = new LinkedList<TRetryListener<Result, Error>>();

    // Current job being executed
    protected TRetryJob<Result, Error> job;

    // Did job already signalized success / fail?
    protected volatile boolean signalized = false;
    protected volatile boolean running = false;
    protected volatile boolean cancel = false;
    protected volatile boolean abort = false;

    // Milliseconds for waiting.
    protected volatile long waitingUntilMilli;

    // State from the last signalization
    protected boolean lastWasSuccess = false;
    protected boolean startedAsBlocking = false;
    protected Result lastResult;
    protected TRetryJobErr<Error> lastError;

    public TRetry(){}

    public TRetry(TRetryStrategy strategy){
        this.retryStrategy = strategy;
    }

    public TRetry(int maxAttempts, TRetryJob<Result, Error> job){
        this.retryStrategy = new TRetryStrategyImpl(maxAttempts);
        this.job = job;
    }

    public TRetry(TRetryJob<Result, Error> job){
        this.job = job;
    }

    @Override
    public void onSuccess(Result result) {
        signalized = true;
        lastWasSuccess = true;
        lastResult = result;
        lastError = null;
        running = false;
        retryStrategy.onSuccess();
        notifyListenerSuccess(result);
    }

    @Override
    public void onFail(TRetryJobErr<Error> error, Boolean abort) {
        signalized = true;
        lastWasSuccess = false;
        lastError = error;
        lastResult = null;
        attempts += 1;
        running = false;
        retryStrategy.onFail();

        if(abort){
            this.abort = true;
            notifyListenerFailed(error);
            return;
        }
        if(retryStrategy.shouldContinue() || cancel){
            notifyListenerFailed(error);
        }else if(!startedAsBlocking){
            final long waitMilli = retryStrategy.getWaitMilli();
            waitingUntilMilli = waitMilli > 0 ?System.currentTimeMillis() + waitMilli : 0;

            job.onRetry(this);

//            if (waitingUntilMilli > 0){
//                new WaitThread(this).start();
//            }else {
//                onWaitFinished();
//            }
        }
    }


    /**
     *
     * @return results of the sync operation
     */
    public Result runSync() throws Exception{
        reset();

        running = true;
        startedAsBlocking = true;
        for(int i = 0; retryStrategy.shouldContinue() && !cancel && !abort; i++){
            //ask strategy if we are going to wait
            final long waitMilli = retryStrategy.getWaitMilli();
            waitingUntilMilli = waitMilli > 0 ? System.currentTimeMillis() + waitMilli : 0;

            //signalize the job it is about to retry
            if(i > 0){
                job.onRetry(this);
            }

            while (!abort && !cancel && waitingUntilMilli > 0 && System.currentTimeMillis() > waitingUntilMilli){
                try {
                    Thread.sleep(10);
                }catch (InterruptedException e){
                    //throw new
                }
            }

            if(!cancel && !abort){
                runAsyncInternal();
            }

            while (!abort && !cancel && !signalized){
                try {
                    Thread.sleep(0,100);
                }catch (InterruptedException e){
                    throw new TRetryFailedException("waiting interrupted", e);
                }
            }

            if(lastWasSuccess){
                break;
            }
        }

        if (abort){
            throw new TRetryAbortException("aborted");
        }
        if (cancel) {
            throw new TRetryCancelledException("cancelled");
        }

        if (lastWasSuccess && !cancel){
            return lastResult;
        }else {
            throw new TRetryFailedException("retry failed", lastError.getThrowable());
        }
    }

    protected void notifyListenerSuccess(Result result){
        for(TRetryListener<Result, Error> listener : listeners){
            listener.onSuccess(result, this);
        }
    }

    protected void notifyListenerFailed(TRetryJobErr<Error> error){
        for(TRetryListener<Result, Error> listener : listeners){
            listener.onFail(error);
        }
    }

    protected void reset(){
        attempts = 0;
        signalized = false;
        lastWasSuccess = false;
        lastResult = null;
        lastError = null;
        running = false;
        cancel = false;
        abort = false;
        retryStrategy.reset();
    }

    protected void runAsyncInternal(){
        signalized = false;
        running = true;
        job.runAsync(this);
    }
}
