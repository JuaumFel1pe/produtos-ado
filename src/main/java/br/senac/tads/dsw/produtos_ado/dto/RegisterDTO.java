package br.senac.tads.dsw.produtos_ado.dto;

import br.senac.tads.dsw.produtos_ado.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Não terá response, porque ele só registra o usuário e não retorna nada (apenas request)
//Como boa prática não deveria passar role, já que o administrador deveria ser criado
//APENAS no banco de dados, isso é apenas para testes e estudos

public record RegisterDTO(
    @NotBlank String login,
    @NotBlank String senha,
    @NotNull UserRole role
) {

}
