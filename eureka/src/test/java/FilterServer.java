import com.choy.thriftplus.eureka.ThriftPlusWithEureka;
import com.choy.thriftplus.eureka.test.gen.ObjectIdGenerator;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.net.ServerSocket;

public class FilterServer {

    public static void main(String[] args){
//        try {
//            ServerSocket socket = new ServerSocket(8083);
//            TServerSocket serverTransport = new TServerSocket(socket);
//            ObjectIdGenerator.Processor processor = new ObjectIdGenerator.Processor(new Service());
//            TServer.Args tServerArgs = new TServer.Args(serverTransport);
//            tServerArgs.processor(processor);
//            TServer server = new TSimpleServer(tServerArgs);
//            System.out.println("launching internal server");
//            server.serve();
//        }catch (IOException | TTransportException e ){
//            e.printStackTrace();
//        }
        ThriftPlusWithEureka server = null;
        try {
            server = new ThriftPlusWithEureka(8083, new ObjectIdGenerator.Processor<>(new Service()), "eureka.vipAddress");
            server.serve();
        }finally {
            if (server != null) server.shutdown();
        }
    }

}
