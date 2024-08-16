package edu.usal.suravicIntegrity.config;

import edu.usal.suravicIntegrity.user.RequestUserDTO;
import edu.usal.suravicIntegrity.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

@Configuration
public class AdminUserConfig {

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    private final String role = "DUENO";

    private final UserService userService;

    @Autowired
    public AdminUserConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public CommandLineRunner createAdminUser() {
        return args -> {
            /* Se crea un contexto de autenticación simulado (no basado en un usuario real autenticado) con el rol necesario
            (ROLE_DUENO) para ejecutar operaciones protegidas dentro de Spring Security. Esto es necesario porque
            algunos métodos dentro de UserService están protegidos por anotaciones como @PreAuthorize,
            que requieren que la autenticación esté presente en el contexto de seguridad. */
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("system", "system", Collections.singleton(new SimpleGrantedAuthority("ROLE_DUENO"))));

            try {
                if (!userService.existsByUsername(username)){
                    RequestUserDTO requestUserDTO = new RequestUserDTO();
                    requestUserDTO.setUsername(username);
                    requestUserDTO.setPassword(password);
                    requestUserDTO.setRole(role);

                    System.out.println(userService.addUser(requestUserDTO));
                }
            } catch (Exception ex){
                System.out.println(ex.fillInStackTrace());
            } finally {
                SecurityContextHolder.clearContext();
            }
        };
    }

}
