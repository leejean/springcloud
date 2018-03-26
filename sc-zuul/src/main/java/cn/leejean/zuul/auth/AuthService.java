package cn.leejean.zuul.auth;

import cn.leejean.zuul.auth.user.User;

public interface AuthService {
    User register(User userToAdd);
    
    String login(String username, String password);
    
    String refresh(String oldToken);
}
