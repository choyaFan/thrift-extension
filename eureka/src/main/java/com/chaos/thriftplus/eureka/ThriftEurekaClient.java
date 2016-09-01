package com.chaos.thriftplus.eureka;

import com.chaos.thriftplus.core.client.ThriftConnectionPool;
import com.chaos.thriftplus.core.client.ThriftPoolConfig;
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

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaClient {
    private static final Config conf = ConfigFactory.load("eureka-service");

    private EurekaClient eurekaClient;
    private ThriftConnectionPool pool;

    public ThriftEurekaClient () {
        EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager manager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        eurekaClient = new DiscoveryClient(manager, new DefaultEurekaClientConfig());
    }

    public TProtocol getConnection () {
        try {
            InstanceInfo serverInfo = eurekaClient.getNextServerFromEureka(conf.getString("eureka.vipAddress"), false);
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

    /**
     * not for common use
     */
    public void close() {
        pool.close();
    }
}
