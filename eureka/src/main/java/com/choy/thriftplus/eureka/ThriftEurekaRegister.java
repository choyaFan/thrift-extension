
package com.choy.thriftplus.eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

public class ThriftEurekaRegister {
    private EurekaServiceBase eurekaServiceBase;

    public ThriftEurekaRegister(int port, String vName) {
        //build Eureka client
        DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();

        EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig(){
            @Override
            public String getHostName(boolean refresh) {
                String hostName = super.getHostName(refresh);
                if(configInstance.getBooleanProperty("eureka.preferIpAddress",true).get()){
                    hostName = "localhost:" + port;
                }
                return hostName;
            }
//            @Override
//            public String getVirtualHostName(){
//                return "localhost:" + port;
//            }

            @Override
            public String getIpAddress() {
                return "127.0.0.1";
            }

            @Override
            public int getNonSecurePort(){
                return port;
            }
        };
        InstanceInfo instanceInfo  = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);

        EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager, new DefaultEurekaClientConfig());

        //register
        eurekaServiceBase = new EurekaServiceBase(applicationInfoManager, eurekaClient, configInstance, vName);
    }

    public void register() {
        eurekaServiceBase.register();
    }

    public void registerDown () {
        eurekaServiceBase.registerDown();
    }
}
