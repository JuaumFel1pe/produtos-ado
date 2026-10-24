package br.senac.tads.dsw.produtos_ado.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.senac.tads.dsw.produtos_ado.dto.CategoriaRequestDTO;
import br.senac.tads.dsw.produtos_ado.dto.CategoriaResponseDTO;
import br.senac.tads.dsw.produtos_ado.model.Categoria;
import br.senac.tads.dsw.produtos_ado.repository.CategoriaRepository;

@Service 
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaResponseDTO> listarTodos(){
        return repository.findAll()
                        .stream()
                        .map(categoria -> new CategoriaResponseDTO(categoria.getId(), categoria.getNome()))
                        .toList();
    }

    public CategoriaResponseDTO buscarPorId(Long id){
        Categoria categoria = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome());
    }

    public CategoriaResponseDTO criar(CategoriaRequestDTO dto){
        Categoria categoria = new Categoria();

        categoria.setNome(dto.nome());
        categoria = repository.save(categoria);

        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome());
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto){
        Categoria categoria = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        categoria.setNome(dto.nome());
        categoria = repository.save(categoria);

        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome());
    }

    public void apagar(Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
