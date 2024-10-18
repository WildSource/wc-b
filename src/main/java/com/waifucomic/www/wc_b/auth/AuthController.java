package com.waifucomic.www.wc_b.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"})
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @GetMapping("/state")
    public boolean getAuthState() {
        return this.service.getAuthState();
    }

    @PostMapping("/validate")
    public boolean validateCredentials(@RequestBody Credentials credentials) {
        return this.service.validateUser(credentials);
    }
}
