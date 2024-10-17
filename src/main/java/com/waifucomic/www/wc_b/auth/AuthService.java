package com.waifucomic.www.wc_b.auth;

import com.waifucomic.www.wc_b.user.User;
import com.waifucomic.www.wc_b.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private boolean authState = false;
    private UserService userService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public boolean validateUser(Credentials credentials) {
        User user = this.userService.getUserByUsername(credentials.getUsername());
        this.authState = encoder.matches(credentials.getPasswd(), user.getPasswd());
        return authState;
    }

    public boolean getAuthState() {
        return this.authState;
    }
}
