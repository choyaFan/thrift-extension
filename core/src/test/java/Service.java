import com.choy.thriftplus.test.gen.ObjectIdGenerator;
import org.apache.thrift.TException;

public class Service implements ObjectIdGenerator.Iface {
    @Override
    public String getObjectId() throws TException {
        return "hello world";
    }
}
