import com.chaos.thriftplus.core.client.ThriftConnectionPool;
import com.chaos.thriftplus.core.client.ThriftPoolConfig;
import com.chaos.thriftplus.test.gen.ObjectIdGenerator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class Client {
    public static void main(String[] args) {
        ThriftPoolConfig config = new ThriftPoolConfig.Builder().setIp("localhost").setPort(8881).setTimeout(3000).build();
        ThriftConnectionPool pool = new ThriftConnectionPool(config);

        while (true) {
            TProtocol p = null;
            try {
                p = pool.getConnection();
                ObjectIdGenerator.Client client = new ObjectIdGenerator.Client(p);
                System.out.println(client.getObjectId());
            } catch (TException e) {
                e.printStackTrace();
            } finally {
                pool.returnConnection(p);
            }
        }


    }
}
