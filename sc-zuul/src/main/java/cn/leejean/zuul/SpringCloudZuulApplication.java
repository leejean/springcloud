package cn.leejean.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 
 * Feign 启动类.<br>
 * @author leejean <br>
 * @version 1.0.0 
 * @date 2018年3月15日 下午3:11:42<br>
 * @see 
 * @since JDK 1.8.0
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class SpringCloudZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulApplication.class, args);
    }
}

