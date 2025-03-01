package com.portalsocial.portalsocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portalsocial.portalsocial.domain.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno,Long> {
    
}
