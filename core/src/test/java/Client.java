//import com.choy.thriftplus.core.client.ThriftConnectionPool;
//import com.choy.thriftplus.core.client.ThriftPoolConfig;
//import com.choy.thriftplus.test.gen.ObjectIdGenerator;
//import org.apache.thrift.TException;
//import org.apache.thrift.protocol.TProtocol;

public class Client {
    public static void main(String[] args) {
//        ThriftPoolConfig config = new ThriftPoolConfig.Builder().setIp("localhost").setPort(8881).setTimeout(3000).build();
//        ThriftConnectionPool pool = new ThriftConnectionPool(config);
//
//        while (true) {
//            TProtocol p = null;
//            try {
//                p = pool.getConnection();
//                ObjectIdGenerator.Client client = new ObjectIdGenerator.Client(p);
//                System.out.println(client.getObjectId());
//            } catch (TException e) {
//                e.printStackTrace();
//            } finally {
//                pool.returnConnection(p);
//            }
//        }


    }
}
