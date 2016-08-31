package com.chaos.thriftplus.core.client;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftPooledObjectFactory implements PooledObjectFactory<TProtocol> {
    private String ip;
    private int port;
    private int timeout;

    public ThriftPooledObjectFactory(ThriftPoolConfig config) {
        this.ip = config.getIp();
        this.port = config.getPort();
        this.timeout = config.getTimeout();
    }

    public PooledObject<TProtocol> makeObject() throws Exception {
        TSocket tSocket = new TSocket(ip, port, timeout);
        TTransport tTransport = new TFramedTransport(tSocket);
        TProtocol tProtocol = new TCompactProtocol(tTransport);
        tProtocol.getTransport().open();
        return new DefaultPooledObject<>(tProtocol);
    }

    public void destroyObject(PooledObject<TProtocol> pooledObject) throws Exception {
        TProtocol tProtocol = pooledObject.getObject();
        TTransport tTransport = tProtocol.getTransport();
        if(tTransport.isOpen()){
            tTransport.close();
        }
    }

    public boolean validateObject(PooledObject<TProtocol> pooledObject) {
        return pooledObject.getObject().getTransport().isOpen();
    }

    public void activateObject(PooledObject<TProtocol> pooledObject) throws Exception {

    }

    public void passivateObject(PooledObject<TProtocol> pooledObject) throws Exception {

    }
}
