package cn.leejean.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvokeControler {
	/**
	 * http://127.0.0.1:8764/hi?name=123
	 */
	@Autowired
	InvokeService helloService;
    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name){
        return "ribbon->"+helloService.hiService(name);
    }
}

