import com.choy.thriftplus.eureka.ThriftPlusWithEureka;
import com.choy.thriftplus.eureka.test.gen.ObjectIdGenerator;

public class FilterBackup {
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
