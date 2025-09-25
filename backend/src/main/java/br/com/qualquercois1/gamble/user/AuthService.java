package br.com.qualquercois1.gamble.user;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final UserMapper userMapper;

    @Lazy
    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        User user = userMapper.RequestDTOToUser(userRequestDTO);
        User usuarioSalvo = userRepository.save(user);
        return userMapper.UserToResponseDTO(usuarioSalvo);
    }

}