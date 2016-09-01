import com.chaos.thriftplus.eureka.ThriftEurekaClient;
import com.chaos.thriftplus.eureka.test.gen.ObjectIdGenerator;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.EurekaClientConfig;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaClientInstance {
    public static void main(String[] args) throws TException {
        ThriftEurekaClient session = new ThriftEurekaClient();
        TProtocol protocol = session.getConnection();
        ObjectIdGenerator.Client client = new ObjectIdGenerator.Client(protocol);
        System.out.println(client.getObjectId());
        session.returnConnection(protocol);
    }
}
