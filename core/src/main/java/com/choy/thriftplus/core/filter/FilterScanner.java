package com.choy.thriftplus.core.filter;

import com.choy.thriftplus.core.util.PackageScanner;

import java.lang.annotation.Annotation;
import java.util.Map;

public class FilterScanner extends PackageScanner {
    private static final Class<ThriftFilter> filter = ThriftFilter.class;

    @Override
    protected boolean isWanted(Class<?> clazz) {
        if(clazz.isAnnotationPresent(filter))
            return true;
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (Annotation ann: annotations)
            if (ann.annotationType().isAnnotationPresent(filter))
                return true;
        return false;
    }

    public FilterScanner ofPackage(String packageName) {
        return (FilterScanner) super.ofPackage(packageName);
    }

    public FilterScanner ofPackage(String packageName, boolean recursive){
        return (FilterScanner) super.ofPackage(packageName, recursive);
    }


    public void initFilter() throws Exception{
        scan();
        System.out.println(allClasses);
        for (Class<?> filter : allClasses){
            AbstractFilter absFilter = (AbstractFilter) filter.newInstance();
            FilterMngr.Instance.addFilters(absFilter);
        }
    }
}
