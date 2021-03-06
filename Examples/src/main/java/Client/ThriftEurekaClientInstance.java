package Client;

import com.choy.thriftplus.core.loadBalancer.DynamicLoadBalancer;
import com.choy.thriftplus.core.loadBalancer.RoundRobinDynamicLoadBalancer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.ExternalService;

public class ThriftEurekaClientInstance {
    public static void main(String[] args) {
        TTransport transport = null;
        try {
            DynamicLoadBalancer<Integer> loadBalancer = new RoundRobinDynamicLoadBalancer<>();
            loadBalancer.add(8081);
            loadBalancer.add(8082);
            transport = new TSocket("localhost", loadBalancer.next());

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