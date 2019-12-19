package Service;

import thrift.ObjectIdGenerator;
import org.apache.thrift.TException;

public class Service implements ObjectIdGenerator.Iface {

    @Override
    public String getObjectId(String id) throws TException {
        System.out.println("this is original");
        return id;
    }
}
