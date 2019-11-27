package com.choy.thriftplus.eureka;

import com.choy.thriftplus.core.loadBalancer.RoundRobinDynamicLoadBalancer;

public class RibbonAlgorithm {
    private final String className;

    private final ThriftEurekaClient client;

    private final String prefixPath;

    private RoundRobinDynamicLoadBalancer<ThriftPlusWithEureka> loadBalancer;

    public RibbonAlgorithm(String className, ThriftEurekaClient client, String prefixPath){
        this.className = className;
        this.client = client;
        this.prefixPath = prefixPath;
        init();
    }

    private void init() {

    }
}
