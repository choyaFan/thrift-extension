package Server;

import com.choy.thriftplus.eureka.ThriftPlusWithEureka;
import thrift.ObjectIdGenerator;
import Service.Service;

public class FilterServerBackup {
    public static void main(String[] args){
        ThriftPlusWithEureka server = null;
        try {
            server = new ThriftPlusWithEureka(8084, new ObjectIdGenerator.Processor<>(new Service()), "eureka.vipAddress");
            server.serve();
        }finally {
            if (server != null) server.shutdown();
        }
    }
}
