package cn.leejean.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvokeControler {
	/**
	 * http://127.0.0.1:8765/hi?name=123
	 */
    @Autowired
    InvokeServiceInterface invokeServiceInterface;
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String sayHi(@RequestParam String name){
        return "feign->"+invokeServiceInterface.hiService(name);
    }
}

