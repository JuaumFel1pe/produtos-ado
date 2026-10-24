package br.senac.tads.dsw.produtos_ado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.produtos_ado.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
