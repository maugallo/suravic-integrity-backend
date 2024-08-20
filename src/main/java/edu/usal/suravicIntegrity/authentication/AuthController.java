package edu.usal.suravicIntegrity.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) { this.tokenService = tokenService; }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        LOG.debug("Token solicitado para usuario: {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token generado: {}", token);
        return token;
    }

    @GetMapping("/refresh")
    public String refreshToken(@RequestHeader("Authorization") String oldToken) {
        // Extract the token part (Bearer token) if it's passed as "Bearer <token>"
        String token = oldToken.startsWith("Bearer ") ? oldToken.substring(7) : oldToken;
        // Decode the token to get Authentication object
        Authentication authentication = tokenService.decodeToken(token);
        // Generate a new token
        return tokenService.generateToken(authentication);
    }

}
