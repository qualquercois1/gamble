package br.com.qualquercois1.gamble.user;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.qualquercois1.gamble.security.JwtService;
import br.com.qualquercois1.gamble.user.dto.LoginRequestDTO;
import br.com.qualquercois1.gamble.user.dto.UserRequestDTO;
import br.com.qualquercois1.gamble.user.dto.UserResponseDTO;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository; 
    private final PasswordEncoder passwordEncoder; 

    @Lazy
    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService,
                       UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequestDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getNomeUsuario(),
                        loginDto.getSenha()
                )
        );

        return jwtService.generateToken(authentication);
    }

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        User user = RequestDTOToUser(userRequestDTO);
        User usuarioSalvo = userRepository.save(user);
        return UserToResponseDTO(usuarioSalvo);
    }

    //Métodos de conversão de DTOS
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