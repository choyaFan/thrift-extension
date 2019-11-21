import com.choy.thriftplus.eureka.test.gen.ExternalService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.net.ServerSocket;

public class ThriftEurekaServer {
    public static void main(String[] args) {
//        ThriftPlusWithEureka server = null;
//        try {
//            server = new ThriftPlusWithEureka(8081, new ExternalService.Processor<>(new FilterService()), "eureka.vipAddress");
//            server.serve();
//        } finally {
//            if (server != null) {
//                server.shutdown();
//            }
//        }
        try {
            ServerSocket socket = new ServerSocket(8081);
            TServerSocket serverTransport = new TServerSocket(socket);
            ExternalService.Processor processor = new ExternalService.Processor(new FilterService());
            TServer.Args tServerArgs = new TServer.Args(serverTransport);
            tServerArgs.processor(processor);
            TServer server = new TSimpleServer(tServerArgs);
            server.serve();
        }catch (IOException | TTransportException e ){
            e.printStackTrace();
        }
    }
}
