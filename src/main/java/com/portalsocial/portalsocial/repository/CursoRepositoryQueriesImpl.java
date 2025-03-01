package com.portalsocial.portalsocial.repository;

import com.portalsocial.portalsocial.domain.model.FotoCurso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CursoRepositoryQueriesImpl implements CursoRepositoryQueries{
    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public FotoCurso save(FotoCurso foto) {
        return this.manager.merge(foto);
    }

    @Override
    @Transactional
    public void delete(FotoCurso foto) {
        this.manager.remove(foto);
    }
}
