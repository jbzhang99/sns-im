package com.inga.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.concurrent.TimeUnit;

/**
 * Created by abing on 2017/6/2.
 */
public class ZkServer2 {

    public static void main(String[] args) {
        try {
            CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181" , new ExponentialBackoffRetry(1000 , 3));
            client.start();
            client.blockUntilConnected();

            ServiceInstanceBuilder<ServiceDetail> sib = ServiceInstance.builder();
            sib.address("127.0.0.1");
            sib.port(8086);
            sib.name("tomcat");
            sib.payload(new ServiceDetail("程序" , 1));

            ServiceInstance<ServiceDetail> instance =sib.build();

            ServiceDiscovery<ServiceDetail> serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetail.class)
                    .client(client)
                    .serializer(new JsonInstanceSerializer<ServiceDetail>(ServiceDetail.class))
                    .basePath(ServiceDetail.REGISTER_ROOT_PATH)
                    .build();

            serviceDiscovery.registerService(instance);
            serviceDiscovery.start();
            TimeUnit.SECONDS.sleep(70);
            serviceDiscovery.close();
            client.close();







        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
