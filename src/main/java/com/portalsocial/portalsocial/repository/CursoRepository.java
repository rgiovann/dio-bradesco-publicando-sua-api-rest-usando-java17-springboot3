package com.portalsocial.portalsocial.repository;

import com.portalsocial.portalsocial.domain.model.FotoCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portalsocial.portalsocial.domain.model.Curso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso,Long> , CursoRepositoryQueries {

     @Query("SELECT f FROM FotoCurso f JOIN f.curso c WHERE f.curso.id = :cursoId")

    Optional<FotoCurso> findFotoById(@Param("cursoId") Long cursoId);

}