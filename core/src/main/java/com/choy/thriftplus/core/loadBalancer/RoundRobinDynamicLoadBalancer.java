package com.choy.thriftplus.core.loadBalancer;


import org.apache.http.annotation.ThreadSafe;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class RoundRobinDynamicLoadBalancer<T> implements DynamicLoadBalancer<T> {
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger size = new AtomicInteger(0);
    private final CopyOnWriteArrayList<T> itemList;
    private final HashMap<T, Integer> itemMap;

    public RoundRobinDynamicLoadBalancer(){
        this.itemList = new CopyOnWriteArrayList<>();
        this.itemMap = new HashMap<>();
    }

    @Override
    public synchronized int add(T item) {
        itemList.add(item);
        itemMap.put(item, size.getAndIncrement());
        return size.get();
    }

    @Override
    public synchronized int remove(T item) {
        int size = this.size.decrementAndGet();
        int index = itemMap.remove(item);
        T endItem = itemList.get(size);
        itemList.set(index, endItem);
        itemList.remove(size);
        itemMap.put(endItem, index);
        return size;
    }

    @Override
    public T next() {
        int size = this.size.get();
        if (size==0)
            return null;
        T item;
        try {
            int index = Math.abs(count.getAndIncrement() % size);
            item = itemList.get(index);
        }catch (ArithmeticException e){
            return null;
        }catch (IndexOutOfBoundsException e1){
            return next();
        }
        return item;
    }

    @Override
    public int size() {
        return size.get();
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return itemList.iterator();
    }
}
