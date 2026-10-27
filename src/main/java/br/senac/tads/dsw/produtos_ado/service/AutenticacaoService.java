package br.senac.tads.dsw.produtos_ado.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.senac.tads.dsw.produtos_ado.dto.RegisterDTO;
import br.senac.tads.dsw.produtos_ado.model.Usuario;
import br.senac.tads.dsw.produtos_ado.repository.UsuarioRepository;

@Service 
public class AutenticacaoService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registrar(RegisterDTO dto){
        if(repository.existsByLogin(dto.login())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este login já está em uso");
        }
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        Usuario novoUsuario = new Usuario(dto.login(), senhaCriptografada, dto.role());

        repository.save(novoUsuario);
    }
}
