package edu.usal.suravicIntegrity.authentication;

import edu.usal.suravicIntegrity.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) { this.tokenService = tokenService; }

    @GetMapping("/login")
    public ResponseEntity<Void> login(Authentication authentication) {
        String token = tokenService.generateToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refreshToken(@RequestHeader("Authorization") String oldToken) {
        String token = oldToken.startsWith("Bearer ") ? oldToken.substring(7) : oldToken; // Extract the token part (Bearer token) if it's passed as "Bearer <token>"

        Authentication authentication = tokenService.decodeToken(token);

        String newToken = tokenService.generateToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + newToken);

        return ResponseEntity.ok().headers(headers).build();
    }

}
