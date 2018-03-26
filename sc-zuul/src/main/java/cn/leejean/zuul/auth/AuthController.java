package cn.leejean.zuul.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.leejean.zuul.auth.secruity.JwtAuthenticationRequest;
import cn.leejean.zuul.auth.secruity.JwtAuthenticationResponse;
import cn.leejean.zuul.auth.user.User;

@RestController
public class AuthController {
	/**
	 * Authorization
	 */
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;
    /**
     * http://127.0.0.1:8766/auth
     * Headers:
     * Content-Type:application/json;charset=utf-8
     * Body raw:
     *  {
			"username":"yilijian",
			"password":"123456"
		}
     * @author leejean <br>
     * @Date 2018年3月19日 下午3:42:26<br>
     * @param authenticationRequest
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException{
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
    /**
     * http://127.0.0.1:8766/refresh
     * 
     * @author leejean <br>
     * @Date 2018年3月19日 下午3:42:08<br>
     * @param request
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }
    /**
     * http://127.0.0.1:8766/auth/register
     * Headers:
     * Content-Type:application/json;charset=utf-8
     * Body raw:
     *  {
			"username":"yilijian",
			"password":"123456",
			"email":"123@163.com"
		}
     * @author leejean <br>
     * @Date 2018年3月19日 下午3:42:04<br>
     * @param addedUser
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public User register(@RequestBody User addedUser) throws AuthenticationException{
        return authService.register(addedUser);
    }
}
