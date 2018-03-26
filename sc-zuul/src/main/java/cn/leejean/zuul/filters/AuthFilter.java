package cn.leejean.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import cn.leejean.zuul.auth.secruity.JwtTokenUtil;

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
	
	private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
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
        logger.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
      	String authHeader = request.getHeader(this.tokenHeader);
          if (authHeader != null && authHeader.startsWith(tokenHead)) {
              final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
              String username = jwtTokenUtil.getUsernameFromToken(authToken);

              logger.info("checking authentication " + username);

              if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                  // 如果我们足够相信token中的数据，也就是我们足够相信签名token的secret的机制足够好
                  // 这种情况下，我们可以不用再查询数据库，而直接采用token中的数据
                  // 本例中，我们还是通过Spring Security的 @UserDetailsService 进行了数据查询
                  // 但简单验证的话，你可以采用直接验证token是否合法来避免昂贵的数据查询
                  UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                  if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                              userDetails, null, userDetails.getAuthorities());
                      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                              request));
                      logger.info("authenticated user " + username + ", setting security context");
                      SecurityContextHolder.getContext().setAuthentication(authentication);
                  }
              }
          }
          return null;

//        chain.doFilter(request, response);
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
//        String accessToken = request.getParameter("token");
//        if(accessToken == null) {
//            log.warn("token is empty");
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            try {
//                ctx.getResponse().getWriter().write("token is empty");
//            }catch (Exception e){
//            	
//            }
//            return null;
//        }
//        log.info("ok");
//        return null;
    }
}
