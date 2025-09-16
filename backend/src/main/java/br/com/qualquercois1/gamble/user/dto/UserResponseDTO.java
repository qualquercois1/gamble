package br.com.qualquercois1.gamble.user.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String nome;
    private String nomeUsuario;
    private String email;
}
