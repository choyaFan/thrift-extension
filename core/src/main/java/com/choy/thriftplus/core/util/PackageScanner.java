package com.choy.thriftplus.core.util;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

public abstract class PackageScanner {
    protected List<ScanTask> scanTasks = new ArrayList<>();
    protected Set<Class<?>> allClasses = new HashSet<>();

    public PackageScanner(){}

    public PackageScanner ofPackage(String packageName) {
        return ofPackage(packageName, true);
    }

    public PackageScanner ofPackage(String packageName, boolean recursive){
        scanTasks.add(new ScanTask(packageName, recursive));
        return this;
    }

    abstract protected boolean isWanted(Class<?> clazz);

    protected void scan() throws Exception{
        Iterator<ScanTask> iter = scanTasks.iterator();
        while (iter.hasNext()){
            ScanTask task = iter.next();
            String dirName = task.packageName.replace('.', '/');
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(dirName);
            while (dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                if ("file".equals(url.getProtocol())) {
                    String absPath = URLDecoder.decode(url.getFile(), "UTF-8");
                    scanFiles(task.packageName, absPath, task.recursive);
                } else {
                    throw new Exception("?????");
                }
            }
        }
    }

    private void scanFiles(String packageName, String absPath, boolean recursive) throws Exception{
        File dir = new File(absPath);
        if (!dir.exists() || !dir.isDirectory()){
            throw new IllegalArgumentException("can't find package name as {" + packageName + "}.");
        }
        scanFiles0(packageName, absPath, recursive);
    }

    private void scanFiles0(String packageName, String absPath, boolean recursive) throws Exception{
        File file = new File(absPath);
        String fileName = file.getName();
        if(file.isFile()){
            String clazzName = fileName.substring(0,fileName.length() - 6);
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + clazzName);
            if (isWanted(clazz))
                allClasses.add(clazz);
            return;
        }
        File[] subFiles = file.listFiles((f) -> (recursive && f.isDirectory()) || f.getName().endsWith(".class"));

        for (File subFile : subFiles){
            String pn = packageName;
            if(subFile.isDirectory())
                pn = pn + '.' + subFile.getName();
            scanFiles0(pn, subFile.getAbsolutePath(), recursive);
        }
    }

    private static class ScanTask{
        String packageName;
        boolean recursive;

        ScanTask(String p, boolean r){
            this.packageName = p;
            this.recursive = r;
        }
    }
}
