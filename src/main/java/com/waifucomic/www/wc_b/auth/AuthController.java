package com.waifucomic.www.wc_b.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"})
    @GetMapping("/state")
    public boolean getAuthState() {
        return this.service.getAuthState();
    }

    @CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"})
    @PostMapping("/validate")
    public boolean validateCredentials(@RequestBody Credentials credentials) {
        return this.service.validateUser(credentials);
    }
}
