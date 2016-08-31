import com.chaos.thriftplus.test.gen.ObjectIdGenerator;
import org.apache.thrift.TException;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class Service implements ObjectIdGenerator.Iface {
    @Override
    public String getObjectId() throws TException {
        return "hello world";
    }
}
