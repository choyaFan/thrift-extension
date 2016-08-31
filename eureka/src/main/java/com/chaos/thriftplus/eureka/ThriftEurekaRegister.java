package com.chaos.thriftplus.eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaRegister {

    public ThriftEurekaRegister(ApplicationInfoManager manager, EurekaClient client, InstanceInfo.InstanceStatus status) {
        register(manager, client, status);
    }

    private void register(ApplicationInfoManager manager, EurekaClient client, InstanceInfo.InstanceStatus status) {
        manager.setInstanceStatus(status);
        waitForRegistrationWithEureka(client);
        System.out.println("Service status " + status.name() + " successful registered");
    }

    private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
        // TODO get conf
        String vipAddress = "";//configInstance.getStringProperty("eureka.vipAddress", "sampleservice.mydomain.net").get();
        InstanceInfo nextServerInfo = null;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            } catch (Throwable e) {
                System.out.println("Waiting ... verifying service registration with eureka ...");

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        eurekaClient.shutdown();
    }
}
