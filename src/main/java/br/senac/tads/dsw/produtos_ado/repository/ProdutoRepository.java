package br.senac.tads.dsw.produtos_ado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.produtos_ado.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
