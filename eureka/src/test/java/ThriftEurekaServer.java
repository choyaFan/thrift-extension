import com.chaos.thriftplus.core.ThriftPlus;
import com.chaos.thriftplus.eureka.ThriftEurekaRegister;
import com.chaos.thriftplus.eureka.ThriftPlusWithEureka;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaServer {
    public static void main(String[] args) {
        // TODO
        ThriftPlus plus = new ThriftPlus.Builder().setPort(9981).setTProcessor(null).build();

        ThriftPlusWithEureka plusWithEureka = new ThriftPlusWithEureka
                .Builder()
                .setThriftPlus(plus)
                .setThriftEurekaRegister(new ThriftEurekaRegister(null, null, null)).build();

        plusWithEureka.serve();
    }
}
