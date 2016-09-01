import com.chaos.thriftplus.eureka.ThriftEurekaClient;
import com.chaos.thriftplus.eureka.test.gen.ObjectIdGenerator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaClientInstance {
    public static void main(String[] args) throws TException, InterruptedException {
        ThriftEurekaClient session = new ThriftEurekaClient();

        while (true) {
            Thread.sleep(1000);
            TProtocol protocol = session.getConnection();
            ObjectIdGenerator.Client client = new ObjectIdGenerator.Client(protocol);
            System.out.println(client.getObjectId());
            session.returnConnection(protocol);
        }
    }
}
