package br.senac.tads.dsw.produtos_ado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.senac.tads.dsw.produtos_ado.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    boolean existsByLogin(String login); //verifica se esse login já existe no banco
    Optional<UserDetails> findByLogin(String login);
}


