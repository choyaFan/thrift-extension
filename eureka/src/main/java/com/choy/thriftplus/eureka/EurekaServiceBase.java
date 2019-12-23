package com.choy.thriftplus.eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClient;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Singleton
public class EurekaServiceBase {

    private final ApplicationInfoManager applicationInfoManager;
    private final EurekaClient eurekaClient;
    private final DynamicPropertyFactory configInstance;
    private final String vName;

    @Inject
    public EurekaServiceBase(ApplicationInfoManager applicationInfoManager,
                             EurekaClient eurekaClient,
                             DynamicPropertyFactory configInstance,
                             String vName) {
        this.applicationInfoManager = applicationInfoManager;
        this.eurekaClient = eurekaClient;
        this.configInstance = configInstance;
        this.vName = vName;
    }

    @PostConstruct
    public void register() {
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        waitForRegistrationWithEureka(eurekaClient);
        System.out.println("Service started and ready to process requests..");
    }

    public void registerDown() {
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
        DiscoveryManager.getInstance().shutdownComponent();
    }

    private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
        String vipAddress = configInstance.getStringProperty(vName, "localhost").get();
        InstanceInfo nextServerInfo = null;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            } catch (Throwable e) {
                System.out.println("Waiting ... verifying service registration with eureka ...");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
