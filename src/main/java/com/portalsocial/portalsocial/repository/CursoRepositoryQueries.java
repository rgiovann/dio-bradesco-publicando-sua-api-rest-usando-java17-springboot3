package com.portalsocial.portalsocial.repository;

import com.portalsocial.portalsocial.domain.model.FotoCurso;

public interface CursoRepositoryQueries {
    FotoCurso save(FotoCurso foto);

    void delete(FotoCurso foto);
}
