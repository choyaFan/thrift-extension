package com.choy.thriftplus.core.filter;

public class TokenAuth extends AbstractFilter{
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
    public String run(String token) throws FilterException{
        if(token.equals("sometoken")){
            token = "123";  //TODO
        }
        else throw new FilterException("filter failed");
        return null;
    }
}
