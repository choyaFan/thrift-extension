package Service;

import com.choy.thriftplus.core.filter.FilterProcessor;
import com.choy.thriftplus.core.filter.FilterScanner;
import com.choy.thriftplus.eureka.ThriftEurekaClient;
import org.apache.thrift.protocol.TProtocol;
import thrift.ExternalService;
import org.apache.thrift.TException;
import thrift.ObjectIdGenerator;

public class FilterService implements ExternalService.Iface {
    @Override
    public String externalService(String token) throws TException {
        if(new FilterCommand("sometoken").execute()) {
            ThriftEurekaClient session = new ThriftEurekaClient();
            TProtocol protocol = session.getConnection("eureka.vipAddress");
            ObjectIdGenerator.Client client = new ObjectIdGenerator.Client(protocol);
            session.returnConnection(protocol);
            return client.getObjectId("123");
        }
        return "token error";
//        throw new TException();
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
