package com.choy.thriftplus.core.filter;

public abstract class AbstractFilter {

    /**
     * @return A string representing filter type: {"pre", "post"}
     */
    abstract public String filterType();

    /**
     * @return the int order of the filter in all filters with the same type
     */
    abstract public int filterOrder();

    /**
     * @return true if filter should run
     */
    abstract public boolean shouldFilter();

    /**
     * @return a string to send back, null if nothing happened.
     */
    abstract public String run(String token) throws FilterException;
}
