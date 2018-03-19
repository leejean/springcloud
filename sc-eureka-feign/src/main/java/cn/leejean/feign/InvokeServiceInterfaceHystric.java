package cn.leejean.feign;

import org.springframework.stereotype.Component;

@Component
public class InvokeServiceInterfaceHystric implements InvokeServiceInterface{
	@Override
    public String hiService(String name) {
        return name + "sorry,error!";
    }
}

