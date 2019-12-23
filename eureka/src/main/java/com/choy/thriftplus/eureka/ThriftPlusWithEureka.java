package com.choy.thriftplus.eureka;

import com.choy.thriftplus.core.ThriftPlus;
import org.apache.thrift.TProcessor;

public class ThriftPlusWithEureka {
    private ThriftPlus plus;
    private ThriftEurekaRegister register;

    public ThriftPlusWithEureka(int port, TProcessor processor, String vName) {
        register = new ThriftEurekaRegister(port, vName);
        register.register();

        plus = new ThriftPlus.Builder()
                .setPort(port)
                .setTProcessor(processor)
                .build();
    }

    public ThriftPlusWithEureka() {
        
    }

    public void serve() {
        plus.serve();
    }

    public void shutdown() {
        register.registerDown();
    }
}
