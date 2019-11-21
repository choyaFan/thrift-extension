package com.choy.thriftplus.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public enum FilterProcessor {
    Instance;

    private final Logger logger = LoggerFactory.getLogger(FilterProcessor.class);

    public void preRouting(String token) throws FilterException{
        runFilters("pre", token);
    }

    public void postRouting() throws FilterException{
        runFilters("post", "123");
    }

    public void runFilters(String filterType, String token) throws FilterException {
        List<AbstractFilter> list = FilterMngr.Instance.getFiltersByType(filterType);
        if(list != null){
            for(AbstractFilter filter: list){
                String result = null;
                try {
                    result = filter.run(token);
                }catch (FilterException e){
                    logger.error("filter failed");
                }
                if(result == null)
                    logger.debug("Filter {}: passed.", filter.getClass().getName());
                else {
                    logger.debug("Filter {}: send some error", filter.getClass().getName());
                    throw new FilterException("Filter send some error");
                }
            }
        }
    }
}
