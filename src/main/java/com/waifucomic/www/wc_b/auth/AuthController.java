package com.waifucomic.www.wc_b.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"})
    @GetMapping("/state")
    public boolean getAuthState() {
        return false;
    }

    @CrossOrigin(origins = {"http://127.0.0.1:4200", "http://localhost:4200"})
    @PostMapping("/validate")
    public boolean validateCredentials(@RequestBody Credentials credentials) {
        logger.info(credentials.toString());
        return getAuthState();
    }
}
