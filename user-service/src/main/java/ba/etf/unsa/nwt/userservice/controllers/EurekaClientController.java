package ba.etf.unsa.nwt.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class EurekaClientController {

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/test/{appName}")
    public List<ServiceInstance> serviceInstancesByAppName(@PathVariable String appName) {
        return this.discoveryClient.getInstances(appName);
    }

}
