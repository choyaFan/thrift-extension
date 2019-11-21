package com.choy.thriftplus.core.client;

import org.apache.thrift.protocol.TProtocol;

public interface ConnectionProvider {
    /**
     * get connection
     * @return
     */
    TProtocol getConnection();

    /**
     * put thr protocol back to the pool
     * @param tProtocol
     */
    void returnConnection(TProtocol tProtocol);

    /**
     * close pool
     */
    void close ();
}
