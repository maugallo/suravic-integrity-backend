package edu.usal.suravicIntegrity.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;

    private String username;

    private String role;

    private Boolean isEnabled;

}
