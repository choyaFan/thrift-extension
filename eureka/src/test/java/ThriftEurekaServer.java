import com.chaos.thriftplus.core.ThriftPlus;
import com.chaos.thriftplus.eureka.ThriftEurekaRegister;
import com.chaos.thriftplus.eureka.ThriftPlusWithEureka;
import com.chaos.thriftplus.eureka.test.gen.ObjectIdGenerator;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaServer {
    public static void main(String[] args) {
        ThriftPlus plus = new ThriftPlus.Builder().setPort(9981).setTProcessor(new ObjectIdGenerator.Processor<>(new Service())).build();

        // TODO
        ThriftPlusWithEureka plusWithEureka = new ThriftPlusWithEureka
                .Builder()
                .setThriftPlus(plus)
                .setThriftEurekaRegister(new ThriftEurekaRegister(null, null, null)).build();

        plusWithEureka.serve();
    }
}
