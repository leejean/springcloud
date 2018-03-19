package cn.leejean.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * springcloud EurekaServer 启动类.<br>
 * @author leejean <br>
 * @version 1.0.0 
 * @date 2018年3月15日 下午3:11:42<br>
 * @see 
 * @since JDK 1.8.0
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaClientApplicationSlave {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplicationSlave.class, args);
    }

    @Value("${server.port}")
    String port;
    @RequestMapping("/hi")
    public String hi(@RequestParam String name) {
        return "hi "+name+",i am from port:" +port;
    }

}

