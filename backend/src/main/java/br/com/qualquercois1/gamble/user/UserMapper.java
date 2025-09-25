package br.com.qualquercois1.gamble.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.qualquercois1.gamble.user.dto.UserRequestDTO;
import br.com.qualquercois1.gamble.user.dto.UserResponseDTO;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //Métodos de conversão de DTOS
    public UserResponseDTO UserToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setNome(user.getNome());
        userResponseDTO.setNomeUsuario(user.getNomeUsuario());
        userResponseDTO.setEmail(user.getEmail());
        return userResponseDTO;
    }

    public User RequestDTOToUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setNome(userRequestDTO.getNome());
        user.setSenha(passwordEncoder.encode(userRequestDTO.getSenha()));
        user.setNomeUsuario(userRequestDTO.getNomeUsuario());
        user.setEmail(userRequestDTO.getEmail());
        user.setRole(Role.ROLE_USER);
        return user;
    }
}
