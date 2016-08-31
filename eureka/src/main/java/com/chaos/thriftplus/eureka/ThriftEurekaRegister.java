package com.chaos.thriftplus.eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaRegister {
    private static final Config conf = ConfigFactory.load("eureka-service");

    public ThriftEurekaRegister(ApplicationInfoManager manager, EurekaClient client, InstanceInfo.InstanceStatus status) {
        register(manager, client, status);
    }

    private void register(ApplicationInfoManager manager, EurekaClient client, InstanceInfo.InstanceStatus status) {
        manager.setInstanceStatus(status);
        waitForRegistrationWithEureka(client);
        System.out.println("Service status " + status.name() + " successful registered");
    }

    private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
        String vipAddress = conf.getString("eureka.vipAddress");
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
