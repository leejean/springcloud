package cn.leejean.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 	<li>pre：路由之前
	<li>routing：路由之时
	<li>post： 路由之后
	<li>error：发送错误调用
	<li>filterOrder：过滤的顺序
	<li>shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
	<li>run：过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
 * @author leejean <br>
 * @version 1.0.0 
 * @date 2018年3月15日 下午5:51:02<br>
 * @see 
 * @since JDK 1.8.0
 */
@Component
public class AuthFilter extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(AuthFilter.class);
    
	@Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){
            	
            }
            return null;
        }
        log.info("ok");
        return null;
    }
}
