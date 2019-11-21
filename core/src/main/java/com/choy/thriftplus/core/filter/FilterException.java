package com.choy.thriftplus.core.filter;

public class FilterException extends Exception {
    public String getErrorMsg() {
        return errorMsg;
    }

    private final String errorMsg;

    public FilterException(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
