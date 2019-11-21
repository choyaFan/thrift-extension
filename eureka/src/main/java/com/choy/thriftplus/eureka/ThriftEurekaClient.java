package com.choy.thriftplus.eureka;

import com.choy.thriftplus.core.client.ThriftConnectionPool;
import com.choy.thriftplus.core.client.ThriftPoolConfig;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.thrift.protocol.TProtocol;

public class ThriftEurekaClient {
    private static final Config conf = ConfigFactory.load("eureka-service");

    private EurekaClient eurekaClient;
    private ThriftConnectionPool pool;

    public ThriftEurekaClient () {
        EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig(){
            @Override
            public String getHostName(boolean refresh) {
                String hostName = super.getHostName(refresh);
                hostName = "localhost";
                return hostName;
            }

            @Override
            public String getIpAddress() {
                return "127.0.0.1";
            }
        };
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager manager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        eurekaClient = new DiscoveryClient(manager, new DefaultEurekaClientConfig());
    }

    public TProtocol getConnection (String vName) {
        try {
            InstanceInfo serverInfo = eurekaClient.getNextServerFromEureka(conf.getString(vName), false);
            ThriftPoolConfig config = new ThriftPoolConfig.Builder().setIp(serverInfo.getIPAddr()).setPort(serverInfo.getPort()).setTimeout(3000).build();
            pool = new ThriftConnectionPool(config);
            return pool.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void returnConnection(TProtocol p) {
        pool.returnConnection(p);
    }

    //not for common use
    public void close() {
        pool.close();
    }
}
