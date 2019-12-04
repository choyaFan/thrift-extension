//import com.choy.thriftplus.eureka.test.gen.ExternalServiceS;
import com.choy.thriftplus.eureka.test.gen.ExternalServiceS;
import com.twitter.util.Future;
import org.apache.thrift.TException;

public class FinagleServer {
    private FinagleServer(){}

    public static class ExImpl implements ExternalServiceS.FutureIface {

        @Override
        public Future<String> externalService(String token) {
            return Future.value("Finagle");
        }
    }

}
