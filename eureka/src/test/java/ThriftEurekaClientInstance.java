import com.choy.thriftplus.eureka.ThriftEurekaClient;
import com.choy.thriftplus.eureka.test.gen.ExternalService;
import com.choy.thriftplus.eureka.test.gen.ObjectIdGenerator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftEurekaClientInstance {
    public static void main(String[] args) throws TException, InterruptedException {
//        ThriftEurekaClient session = new ThriftEurekaClient();
//
//        TProtocol protocol = session.getConnection("eureka.vipAddress");
//        ExternalService.Client client = new ExternalService.Client(protocol);
//        System.out.println(client.externalService("sometoken"));
//        session.returnConnection(protocol);

        TTransport transport = null;
        try {
            transport = new TSocket("localhost", 8081);

            TProtocol protocol = new TBinaryProtocol(transport);

            ExternalService.Client client = new ExternalService.Client(protocol);

            transport.open();

            System.out.println(client.externalService("sometoken"));
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
    }
}