package com.choy.thriftplus.core.filter.filters;

import com.choy.thriftplus.core.filter.AbstractFilter;
import com.choy.thriftplus.core.filter.FilterException;
import com.choy.thriftplus.core.filter.ThriftFilter;

@ThriftFilter
public class TokenAuth extends AbstractFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String run(String token) throws FilterException {
        if(token.equals("sometoken")){
            token = "123";  //TODO
            System.out.println("hello?");
        }
        else throw new FilterException("filter failed");
        return null;
    }
}
