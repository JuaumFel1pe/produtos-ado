package br.senac.tads.dsw.produtos_ado.dto;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    Double preco,
    String nomeCategoria
) {
    
}
