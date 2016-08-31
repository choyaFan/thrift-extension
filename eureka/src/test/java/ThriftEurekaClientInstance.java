import com.chaos.thriftplus.eureka.ThriftEurekaClient;
import com.chaos.thriftplus.eureka.test.gen.ObjectIdGenerator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaClientInstance {
    public static void main(String[] args) throws TException {
        // TODO
        ThriftEurekaClient client = new ThriftEurekaClient(null, null);

        TProtocol protocol = client.getConnection();
        ObjectIdGenerator.Client client1 = new ObjectIdGenerator.Client(protocol);
        client1.getObjectId();
        client.returnConnection(protocol);
    }
}
