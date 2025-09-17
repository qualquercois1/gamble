package br.com.qualquercois1.gamble.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.qualquercois1.gamble.security.dto.TokenDTO;
import br.com.qualquercois1.gamble.user.dto.LoginRequestDTO;
import br.com.qualquercois1.gamble.user.dto.UserRequestDTO;
import br.com.qualquercois1.gamble.user.dto.UserResponseDTO;
import ch.qos.logback.core.subst.Token;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = authService.register(userRequestDTO);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.login(loginRequestDTO);
        return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
    }
    
}
