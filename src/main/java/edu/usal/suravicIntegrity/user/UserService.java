package edu.usal.suravicIntegrity.user;

import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // GET METHODS:
    public List<UserResponseDTO> findUsers(Boolean isEnabled) {
        List<User> users = userRepository.findByIsEnabled(isEnabled);

        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el usuario solicitado con id " + id));
    }

    public UserResponseDTO findUserResponseById(Long id) {
        User user = this.findUserById(id);

        return userMapper.toDTO(user);
    }

    // CREATE METHOD:
    public String addUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setIsEnabled(true);
        userRepository.save(user);

        return "Usuario creado correctamente";
    }

    // PUT METHOD:
    public String updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = this.findUserById(id);

        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(userRequestDTO.getRole());

        userRepository.save(user);

        return "Usuario actualizado correctamente";
    }

    // DELETE/RECOVER METHOD:
    public String toggleIsEnabled(Long id) {
        User user = this.findUserById(id);

        user.setIsEnabled(!user.getIsEnabled());
        userRepository.save(user);

        return (user.getIsEnabled() ? "Usuario recuperado correctamente" : "Usuario eliminado correctamente");
    }

}
