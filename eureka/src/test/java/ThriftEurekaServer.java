import com.chaos.thriftplus.eureka.ThriftPlusWithEureka;
import com.chaos.thriftplus.eureka.test.gen.ObjectIdGenerator;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaServer {
    public static void main(String[] args) {
        ThriftPlusWithEureka server = null;
        try {
            server = new ThriftPlusWithEureka(8881, new ObjectIdGenerator.Processor<>(new Service()));
            server.serve();
        } finally {
            if (server != null) {
                server.shutdown();
            }
        }
    }
}
