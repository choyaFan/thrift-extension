package com.choy.thriftplus.core.client;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.inferred.freebuilder.FreeBuilder;
import org.jetbrains.annotations.Nullable;

@FreeBuilder
public interface ThriftPoolConfig {
    String getIp();
    int getPort();
    int getTimeout();

    @Nullable
    GenericObjectPoolConfig getPoolConfig();

    class Builder extends ThriftPoolConfig_Builder {}
}
