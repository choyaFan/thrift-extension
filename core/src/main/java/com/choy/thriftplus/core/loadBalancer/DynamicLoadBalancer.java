package com.choy.thriftplus.core.loadBalancer;

public interface DynamicLoadBalancer<T> extends LoadBalancer<T> {
    int add(T item);

    int remove(T item);
}
