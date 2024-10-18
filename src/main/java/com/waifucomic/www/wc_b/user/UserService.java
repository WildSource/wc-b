package com.waifucomic.www.wc_b.user;

import com.waifucomic.www.wc_b.auth.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private UserRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(Credentials credentials) {
        this.repository.save(
                new User(
                        credentials.getUsername(),
                        encoder.encode(
                                credentials.getPasswd()
                        )
                )
        );
    }

    public User getUserByUsername(String username) {
        return this.repository.findByUsername(username).orElseThrow(NoSuchElementException::new);
    }
}
