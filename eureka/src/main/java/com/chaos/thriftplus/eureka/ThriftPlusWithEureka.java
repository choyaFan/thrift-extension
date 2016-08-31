package com.chaos.thriftplus.eureka;

import com.chaos.thriftplus.core.ThriftPlus;
import org.inferred.freebuilder.FreeBuilder;

/**
 * Created by zcfrank1st on 8/31/16.
 */
@FreeBuilder
public interface ThriftPlusWithEureka {
    ThriftPlus getThriftPlus();
    ThriftEurekaRegister getThriftEurekaRegister();

    default void serve() {
        getThriftPlus().serve();
    }

    class Builder extends ThriftPlusWithEureka_Builder {}
}
