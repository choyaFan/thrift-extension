import com.choy.thriftplus.core.retry.*;
import com.choy.thriftplus.eureka.test.gen.ExternalService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class RetryAsync {
    public static void main(String[] args)throws Exception{
        final TRetryStrategy retryStrategy = new TRetryStrategyBackoff.Builder()
                                                                        .setMaxElapsedTimeMillis(1000*60*5)
                                                                        .build();
        
        final TRetry<Boolean, Throwable> retry = new TRetry<Boolean, Throwable>(retryStrategy.copy());

        TTransport transport = null;
        ExternalService.Client client;
        transport = new TSocket("localhost", 8081);

        TProtocol protocol = new TBinaryProtocol(transport);

        client = new ExternalService.Client(protocol);

        transport.open();
        
        retry.setJob(new TRetryJonSimpleSafe<Boolean, Throwable>() {
            @Override
            public void runAsyncNoException(TCallBack<Boolean, Throwable> callBack) throws Throwable {
                try {
                    System.out.println(client.externalService("sometoken"));

                    callBack.onSuccess(true);
                }catch (Exception e){
                    callBack.onFail(new TRetryJobErr(e), false);
                }
            }
        });
        
        retry.addListener(new TRetryListener<Boolean, Throwable>() {
            @Override
            public void onSuccess(Boolean aBoolean, TRetry<Boolean, Throwable> retry) {
                //oh, we success
            }

            @Override
            public void onFail(TRetryJobErr<Throwable> error) {
                //well, we tried
            }
        });
        
        TFuture<Boolean, Throwable> future = retry.runAsync();
    }
}
