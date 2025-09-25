package br.com.qualquercois1.gamble.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.qualquercois1.gamble.user.dto.UserResponseDTO;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    //Métodos padrões de CRUD
    public UserResponseDTO FindUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return userMapper.UserToResponseDTO(user);
    }

    public List<UserResponseDTO> ShowAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> usersResult = new ArrayList<>();

        for(User a : users) {
            usersResult.add(userMapper.UserToResponseDTO(a));
        }
        return usersResult;
    }

    public Boolean DeleteUserById(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByNomeUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}