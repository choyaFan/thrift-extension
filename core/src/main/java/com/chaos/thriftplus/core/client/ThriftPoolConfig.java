package com.chaos.thriftplus.core.client;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.inferred.freebuilder.FreeBuilder;

import javax.annotation.Nullable;

/**
 * Created by zcfrank1st on 8/31/16.
 */
@FreeBuilder
public interface ThriftPoolConfig {
    String getIp();
    int getPort();
    int getTimeout();

    @Nullable
    GenericObjectPoolConfig getPoolConfig();

    class Builder extends ThriftPoolConfig_Builder {}
}
