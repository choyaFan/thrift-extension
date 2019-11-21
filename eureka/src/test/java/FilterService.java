import com.choy.thriftplus.core.filter.FilterException;
import com.choy.thriftplus.core.filter.FilterProcessor;
import com.choy.thriftplus.eureka.ThriftEurekaClient;
import com.choy.thriftplus.eureka.test.gen.ExternalService;
import com.choy.thriftplus.eureka.test.gen.ObjectIdGenerator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class FilterService implements ExternalService.Iface {
    @Override
    public String externalService(String token) throws TException {
        if(doPreFilter(token)){
//            TTransport transport = null;
//            try {
//                transport = new TSocket("localhost", 8083);
//
//                TProtocol protocol = new TBinaryProtocol(transport);
//
//                ObjectIdGenerator.Client client = new ObjectIdGenerator.Client(protocol);
//
//                transport.open();
//
//                return client.getObjectId("123");
//            }catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                transport.close();
//            }

        ThriftEurekaClient session = new ThriftEurekaClient();
        TProtocol protocol = session.getConnection("eureka.vipAddress");
        ObjectIdGenerator.Client client = new ObjectIdGenerator.Client(protocol);
        session.returnConnection(protocol);
        return client.getObjectId("123");
        }
        return "token error";
    }

    public boolean doPreFilter(String token){
        try {
            FilterProcessor.Instance.preRouting(token);
        } catch (FilterException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
