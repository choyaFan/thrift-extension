package com.chaos.thriftplus.core.client;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.thrift.protocol.TProtocol;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftConnectionPool implements ConnectionProvider {
    private GenericObjectPool<TProtocol> objectPool;

    public ThriftConnectionPool (ThriftPoolConfig config) {
        objectPool = new GenericObjectPool<>(new ThriftPooledObjectFactory(config));

        GenericObjectPoolConfig poolConfig = config.getPoolConfig();
        if (null != poolConfig)
            objectPool.setConfig(config.getPoolConfig());
    }

    @Override
    public TProtocol getConnection() {
        try {
            return objectPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("getConnection出现异常", e);
        }
    }

    @Override
    public void returnConnection(TProtocol tProtocol) {
        try {
            objectPool.returnObject(tProtocol);
        } catch (Exception e) {
            throw new RuntimeException("returnConnection出现异常", e);
        }
    }

    @Override
    public void close() {
        try {
            objectPool.close();
        } catch (Exception e) {
            throw new RuntimeException("close出现异常", e);
        }
    }
}
