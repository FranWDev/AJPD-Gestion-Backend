package org.dubini.gestion.service;

import org.dubini.gestion.config.AccessKeyProperties;
import org.dubini.gestion.dto.JwtResponse;
import org.dubini.gestion.dto.LoginRequest;
import org.dubini.gestion.security.JwtProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;
    private final AccessKeyProperties accessKeyProperties;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtProvider jwtProvider, AccessKeyProperties accessKeyProperties, PasswordEncoder passwordEncoder) {
        this.jwtProvider = jwtProvider;
        this.accessKeyProperties = accessKeyProperties;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtResponse login(LoginRequest request) {
        if (request.getAccessKey() == null || !passwordEncoder.matches(request.getAccessKey(), accessKeyProperties.getAccessKey())) {
            throw new BadCredentialsException("Invalid access key");
        }
        String token = jwtProvider.generateToken();
        return new JwtResponse(token);
    }
}
