
package org.acme.services;

import org.acme.models.User;
import org.mindrot.jbcrypt.BCrypt;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.acme.repository.UserRepository;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@ApplicationScoped

public class AuthService {
    @Inject
    UserRepository userRepository;

    @Transactional
    public User register(String username, String password, String roles) {
        User user = new User();
        user.username = username;
        user.passwordHash = hashPassword(password);
        user.roles = roles;
        userRepository.persist(user);
        return user;
    }

    public User validateCredentials(String username, String password) {
        log.info("Validating credentials for user: {}", username);

        User user = userRepository.findByUsername(username);
        log.info("User found: {}", user != null ? user.username : "null");
        if (user != null && verifyPassword(password, user.passwordHash)) {
            log.info("Password verified for user: {}", username);
            return user;
        }
        log.info("Invalid credentials for user: {}", username);
        return null;
    }

    public String generateAccessToken(User user) {
        Set<String> rolesSet = new HashSet<>();
        if (user.roles != null) {
            for (String r : user.roles.split(",")) {
                rolesSet.add(r.trim());
            }
        }
        return Jwt.issuer("auth-service")
                .upn(user.username)
                .groups(rolesSet)
                .expiresIn(Duration.ofHours(1))
                .sign();
    }

    public String generateRefreshToken(User user) {
        String refreshToken = UUID.randomUUID().toString();
        user.refreshToken = refreshToken;
        userRepository.persist(user);
        return refreshToken;
    }

    public boolean validateRefreshToken(User user, String refreshToken) {
        return user.refreshToken != null && user.refreshToken.equals(refreshToken);
    }

    // Métodos de hash y verificación de contraseña (BCrypt)
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
