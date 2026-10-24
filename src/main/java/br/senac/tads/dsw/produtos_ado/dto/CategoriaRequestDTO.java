package br.senac.tads.dsw.produtos_ado.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
    @NotBlank String nome
) {

}
