package cn.leejean.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * 负载均衡客户端  启动类.<br>
 * @author leejean <br>
 * @version 1.0.0 
 * @date 2018年3月15日 下午3:11:42<br>
 * @see 
 * @since JDK 1.8.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class SpringCloudRibbonApplication {

	public static void main(String[] args) {
        SpringApplication.run(SpringCloudRibbonApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

