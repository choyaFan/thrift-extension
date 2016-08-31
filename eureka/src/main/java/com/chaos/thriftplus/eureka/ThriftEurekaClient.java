package com.chaos.thriftplus.eureka;

import com.chaos.thriftplus.core.client.ThriftConnectionPool;
import com.chaos.thriftplus.core.client.ThriftPoolConfig;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import org.apache.thrift.protocol.TProtocol;

/**
 * Created by zcfrank1st on 8/31/16.
 */
public class ThriftEurekaClient {
    private EurekaClient eurekaClient;
    private ThriftConnectionPool pool;

    public ThriftEurekaClient (EurekaInstanceConfig instanceConfig, EurekaClientConfig clientConfig) {
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager manager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        eurekaClient = new DiscoveryClient(manager, clientConfig);
    }

    public TProtocol getConnection () {
        // TODO get conf
        try {
            InstanceInfo serverInfo = eurekaClient.getNextServerFromEureka("", false);
            ThriftPoolConfig config = new ThriftPoolConfig.Builder().setIp(serverInfo.getIPAddr()).setPort(serverInfo.getPort()).setTimeout(3000).build();
            pool = new ThriftConnectionPool(config);
            return pool.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("get thrift connection error");
        }
    }

    public void returnConnection(TProtocol p) {
        pool.returnConnection(p);
    }

    public void close() {
        pool.close();
    }
}
