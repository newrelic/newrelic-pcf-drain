/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.newrelic.cf.metrics.newrelic;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;


@Configuration
public class MonitoringConfiguration {

    @Bean
    @ConditionalOnProperty("vsphere.host")
    InventoryNavigator inventoryNavigator(@Value("${vsphere.host}") String host,
            @Value("${vsphere.username}") String username,
            @Value("${vsphere.password}") String password) throws MalformedURLException,
            RemoteException {
        URL url = new URL(String.format("https://%s/sdk", host));
        ServiceInstance serviceInstance = new ServiceInstance(url, username, password, true);
        return new InventoryNavigator(serviceInstance.getRootFolder());
    }
}
