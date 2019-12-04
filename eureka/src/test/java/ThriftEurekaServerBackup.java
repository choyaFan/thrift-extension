import com.choy.thriftplus.eureka.test.gen.ExternalServiceS;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.net.ServerSocket;

public class ThriftEurekaServerBackup {
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(8082);
            TServerSocket serverTransport = new TServerSocket(socket);
            ExternalServiceS.Processor processor = new ExternalServiceS.Processor(new FilterService());
            TServer.Args tServerArgs = new TServer.Args(serverTransport);
            tServerArgs.processor(processor);
            TServer server = new TSimpleServer(tServerArgs);
            server.serve();
        }catch (IOException | TTransportException e ){
            e.printStackTrace();
        }
    }
}
