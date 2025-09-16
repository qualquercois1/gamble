package br.com.qualquercois1.gamble.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.qualquercois1.gamble.user.dto.UserRequestDTO;
import br.com.qualquercois1.gamble.user.dto.UserResponseDTO;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        User user = RequestDTOToUser(userRequestDTO);
        userRepository.save(user);
        return UserToResponseDTO(user);
    }

    private UserResponseDTO UserToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setNome(user.getNome());
        userResponseDTO.setNomeUsuario(user.getNomeUsuario());
        userResponseDTO.setEmail(user.getEmail());
        return userResponseDTO;
    }

    private User RequestDTOToUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setNome(userRequestDTO.getNome());
        user.setSenha(passwordEncoder.encode(userRequestDTO.getSenha()));
        user.setNomeUsuario(userRequestDTO.getNomeUsuario());
        user.setEmail(userRequestDTO.getEmail());
        user.setRole(Role.ROLE_USER);
        return user;
    }
}
