package com.chaos.thriftplus.eureka;

import com.chaos.thriftplus.core.ThriftPlus;
import org.apache.thrift.TProcessor;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftPlusWithEureka {
    private ThriftPlus plus;

    public ThriftPlusWithEureka(int port, TProcessor processor) {
        new ThriftEurekaRegister();

        plus = new ThriftPlus.Builder()
                .setPort(port)
                .setTProcessor(processor)
                .build();
    }

    public void serve() {
        plus.serve();
    }
}
