import com.choy.thriftplus.core.filter.FilterProcessor;
import com.choy.thriftplus.core.filter.FilterScanner;
import com.choy.thriftplus.core.retry.TRetryCancelledException;
import com.choy.thriftplus.core.retry.TRetryFailedException;
import com.choy.thriftplus.eureka.ThriftEurekaClient;
import com.choy.thriftplus.eureka.test.gen.ExternalService;
import com.choy.thriftplus.eureka.test.gen.ObjectIdGenerator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

import javax.sound.midi.Track;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FilterService implements ExternalService.Iface {
    @Override
    public String externalService(String token) throws TException{
//        if(new FilterCommand("sometoken").execute()) {
//            ThriftEurekaClient session = new ThriftEurekaClient();
//            TProtocol protocol = session.getConnection("eureka.vipAddress");
//            ObjectIdGenerator.Client client = new ObjectIdGenerator.Client(protocol);
//            session.returnConnection(protocol);
//            return client.getObjectId("123");
//        }
//        return "token error";
        throw new TException();
    }

    public boolean doPreFilter(String token){
        try {
            FilterScanner scanner = new FilterScanner();
            scanner.ofPackage("com.choy.thriftplus.core.filter.filters")
                    .initFilter();
            FilterProcessor.Instance.preRouting(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
