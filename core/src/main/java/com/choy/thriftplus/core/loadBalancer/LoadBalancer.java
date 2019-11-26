package com.choy.thriftplus.core.loadBalancer;

public interface LoadBalancer<T> extends Iterable<T>{
    T next();

    int size();

}
