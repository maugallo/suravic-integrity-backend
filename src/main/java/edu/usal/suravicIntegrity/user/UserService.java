package edu.usal.suravicIntegrity.user;

import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // GET METHODS:
    public List<ResponseUserDTO> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> userMapper.toDTO(user))
                .collect(Collectors.toList());
    }

    public ResponseUserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el usuario solicitado con id " + id));

        return userMapper.toDTO(user);
    }

    // CREATE METHOD:
    public String addUser(RequestUserDTO requestUserDTO) {
        User user = userMapper.toEntity(requestUserDTO);
        user.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);

        return "Usuario creado correctamente";
    }

    // PUT METHOD:
    public String updateUser(Long id, RequestUserDTO requestUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el usuario solicitado con id " + id));

        user.setUsername(requestUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));
        user.setRole(requestUserDTO.getRole());

        userRepository.save(user);

        return "Usuario actualizado correctamente";
    }

    // DELETE/RECOVER METHOD:
    public String toggleIsEnabled(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el usuario solicitado con id " + id));

        user.setEnabled(!user.getEnabled());
        userRepository.save(user);

        return (user.getEnabled() ? "Usuario recuperado correctamente" : "Usuario eliminado correctamente");
    }

}
