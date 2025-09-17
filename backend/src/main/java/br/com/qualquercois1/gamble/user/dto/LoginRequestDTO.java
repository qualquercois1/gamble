package br.com.qualquercois1.gamble.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDTO {
    private String nomeUsuario;
    private String senha;
}
