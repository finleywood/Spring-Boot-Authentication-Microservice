package com.rubric.service.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.hash.Hashing;
import com.rubric.service.auth.entity.User;
import com.rubric.service.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class UserService {

    private String jwtSecret = "tPYRZfVD265SNStk04XG9mVKOWa0VXhL";

    @Autowired
    private UserRepository userRepository;

    public User getUserById(long id) {
        return userRepository.getReferenceById(id);
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    public String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    public String getJwt(long id) {
        Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
        String token = JWT.create().withClaim("uid", id).withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 24 * 1000)).sign(algorithm);
        return token;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailEquals(email);
    }

}
