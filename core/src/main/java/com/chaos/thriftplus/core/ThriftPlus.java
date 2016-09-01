package com.chaos.thriftplus.core;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.inferred.freebuilder.FreeBuilder;

/**
 * Created by zcfrank1st on 8/31/16.
 */
@FreeBuilder
public interface ThriftPlus {
    int getPort();
    TProcessor getTProcessor();

    default void serve() {
        Builder.server.serve();
    }

    class Builder extends ThriftPlus_Builder {
        static TServer server;
        @Override
        public ThriftPlus build() {
            TNonblockingServerSocket tnbSocketTransport;
            try {
                tnbSocketTransport = new TNonblockingServerSocket(getPort());
            } catch (TTransportException e) {
                throw new RuntimeException(e);
            }
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
            tnbArgs.processor(getTProcessor());
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());
            server = new TNonblockingServer(tnbArgs);
            return super.build();
        }
    }


}
