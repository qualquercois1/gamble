package br.com.qualquercois1.gamble.user.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String nome;
    private String senha;
    private String nomeUsuario;
    private String email;
}
