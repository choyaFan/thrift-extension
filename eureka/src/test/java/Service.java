import com.choy.thriftplus.eureka.test.gen.ObjectIdGenerator;
import org.apache.thrift.TException;

public class Service implements ObjectIdGenerator.Iface {

    @Override
    public String getObjectId(String id) throws TException {
        return id;
    }
}
