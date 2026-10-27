package br.senac.tads.dsw.produtos_ado.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    BigDecimal preco, 
    String nomeCategoria
) {
    
}
