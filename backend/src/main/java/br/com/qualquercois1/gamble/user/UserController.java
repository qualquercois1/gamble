package br.com.qualquercois1.gamble.user;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.qualquercois1.gamble.user.dto.UserResponseDTO;
import br.com.qualquercois1.gamble.user.dto.UserUpdateDTO;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> FindUserById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.FindUserById(id);

        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> showAllUsers() {
        List<UserResponseDTO> result = userService.ShowAllUsers();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteUserById(@PathVariable Long id) {
        if(userService.DeleteUserById(id)){
            return ResponseEntity.ok("Usuário deletado.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
    }

    @PutMapping("/{id}")
    //verificar se é um admin fazendo update ou um usuário fazendo update no próprio perfil
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<UserResponseDTO> UpdateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserResponseDTO userResponseDTO = userService.UpdateUser(id, userUpdateDTO);
        return ResponseEntity.ok(userResponseDTO);
    }
}
