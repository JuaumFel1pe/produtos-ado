package br.senac.tads.dsw.produtos_ado.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoRequestDTO(
    @NotBlank  String nome,
    @NotNull @Positive  BigDecimal preco,
    @NotNull  Long categoriaId
) {

}
