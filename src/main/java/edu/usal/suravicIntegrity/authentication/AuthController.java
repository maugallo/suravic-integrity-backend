package edu.usal.suravicIntegrity.authentication;

import edu.usal.suravicIntegrity.security.TokenService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) { this.tokenService = tokenService; }

    @GetMapping("/login")
    public ResponseEntity<Void> login(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        String refreshToken = tokenService.generateRefreshToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .build();
        headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refreshToken(@CookieValue("refreshToken") String refreshToken) {
        Authentication authentication = tokenService.decodeRefreshToken(refreshToken);

        String newToken = tokenService.generateToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + newToken);

        return ResponseEntity.ok().headers(headers).build();
    }

}
