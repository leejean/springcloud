package cn.leejean.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "sc-eureka-client",fallback = InvokeServiceInterfaceHystric.class)
public interface InvokeServiceInterface {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String hiService(@RequestParam(value = "name") String name);
}

