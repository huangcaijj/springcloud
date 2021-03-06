package com.study.springcloud.service.lucy.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Created by IntelliJ IDEA.
 *@author :Administratior
 *@date:2017/12/5
 *To change this template use File|Settings|File Templates.
 * 当client向server注册时，它会提供一些元数据，例如主机和端口，URL，主页等。Eureka server 从每个client实例接收心跳消息。 如果心跳超时，则通常将该实例从注册server中删除。
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
public class CloudEurekaClientLucyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudEurekaClientLucyApplication.class, args);
    }

    private static final Logger LOG = Logger.getLogger(CloudEurekaClientLucyApplication.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "infoError")
    public String callHome(){
        LOG.log(Level.INFO, "calling trace service-lucy  ");
        return restTemplate.getForObject("http://localhost:8770/api-b/infos?name='sfda'&token=123", String.class);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("/infos")
    @HystrixCommand(fallbackMethod = "infoError")
    public String home(@RequestParam String name) {
        return "info "+name+",i am from port:" +port;
    }

    public String infoError(String name) {
        return "info,"+name+",sorry,error!";
    }


    @Bean
    public AlwaysSampler defaultSampler(){
        return new AlwaysSampler();
    }
}
