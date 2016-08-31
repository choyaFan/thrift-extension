import com.chaos.thriftplus.core.ThriftPlus;
import com.chaos.thriftplus.test.gen.ObjectIdGenerator;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class Server {
    public static void main(String[] args) {
        new ThriftPlus.Builder()
                .setPort(8881)
                .setTProcessor(new ObjectIdGenerator.Processor<>(new Service()))
                .build()
                .serve();
    }
}
