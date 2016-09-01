/*
 * Copyright 2012 Netflix, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.chaos.thriftplus.eureka;

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

    public ThriftEurekaRegister() {
        DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();

        EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        InstanceInfo instanceInfo  = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);

        EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager, new DefaultEurekaClientConfig());

        EurekaServiceBase eurekaServiceBase = new EurekaServiceBase(applicationInfoManager, eurekaClient, configInstance);
        eurekaServiceBase.register();
    }
}
