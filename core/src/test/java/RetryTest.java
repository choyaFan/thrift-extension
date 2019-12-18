import com.choy.thriftplus.core.retry.*;
import org.junit.Test;

import java.io.IOException;

public class RetryTest {
    @Test
    public static void main(String[] args){
        final TRetryStrategy retryStrategy = new TRetryStrategyBackoff.Builder()
                                                                        .setMaxElapsedTimeMillis(1000*60*5)
                                                                        .build();
        
        final TRetry<Boolean, Throwable> retry = new TRetry<Boolean, Throwable>(retryStrategy.copy());
        
        retry.setJob(new TRetryJonSimpleSafe<Boolean, Throwable>() {
            @Override
            public void runAsyncNoException(TCallBack<Boolean, Throwable> callBack) throws Throwable {
                try {
                    
                }catch (Exception e){
                    callBack.onFail(new TRetryJobErr(e), false);
                }
            }
        });
    }
}
