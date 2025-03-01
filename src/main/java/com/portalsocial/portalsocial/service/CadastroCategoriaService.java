package com.portalsocial.portalsocial.service;

import com.portalsocial.portalsocial.domain.exception.CategoriaNaoEncontradaException;
import com.portalsocial.portalsocial.domain.model.Categoria;
import com.portalsocial.portalsocial.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastroCategoriaService {
    private static final String MSG_ESTADO_EM_USO = "Categoria de código %d não pode ser removido, pois está em uso.";
    private final CategoriaRepository categoriaRepository;

    public CadastroCategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;

    }

/*
    public List<Categoria> listar() {

        return CategoriaRepository.findAll();

    }

    @Transactional
    public Categoria salvar(Categoria Categoria) {

        return CategoriaRepository.save(Categoria);
    }

    @Transactional
    public void excluir(Long CategoriaId) {
        try {

            CategoriaRepository.deleteById(CategoriaId);

            CategoriaRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            try {
                throw new CategoriaNaoEncontradaException(CategoriaId);
            } catch (CategoriaNaoEncontradaException ex) {
                throw new RuntimeException(ex);
            }
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, CategoriaId));
        }

    }
*/
    public Categoria buscarOuFalhar(Long categoriaId) throws CategoriaNaoEncontradaException {
        return categoriaRepository.findById(categoriaId).orElseThrow(() -> new CategoriaNaoEncontradaException(categoriaId));
    }
}
