package com.choy.thriftplus.core.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum FilterMngr {
    Instance;

    private static final String PRE = "pre";
    private static final String POST = "post";

    private CopyOnWriteArrayList<AbstractFilter>[] filters = new CopyOnWriteArrayList[2];

    FilterMngr(){
        filters[0] = new CopyOnWriteArrayList<>();
        filters[1] = new CopyOnWriteArrayList<>();
        addFilters(new TokenAuth());
    }

    public void addFilters(AbstractFilter... filters){
        for (AbstractFilter filter : filters){
            int index = index(filter.filterType());
            this.filters[index].add(filter);
        }
    }

    public List<AbstractFilter> getFiltersByType(String filterType){
        int index = index(filterType);
        if(index < 0)
            return new ArrayList<>();
        return filters[index];
    }

    private int index(String filterType){
        switch (filterType){
            case PRE:
                return 0;
            case POST:
                return 1;
            default:
                return -1;
        }
    }
}
