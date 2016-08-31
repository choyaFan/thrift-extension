package com.chaos.thriftplus.core.client;

import org.apache.thrift.protocol.TProtocol;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public interface ConnectionProvider {
    /**
     * 获取连接
     * @return
     */
    TProtocol getConnection();

    /**
     * 将protocol放回连接池
     * @param tProtocol
     */
    void returnConnection(TProtocol tProtocol);

    /**
     * 关闭连接池
     */
    void close ();
}
