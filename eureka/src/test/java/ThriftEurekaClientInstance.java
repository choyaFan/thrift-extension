import com.choy.thriftplus.eureka.test.gen.ExternalService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftEurekaClientInstance {
    public static void main(String[] args) {
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