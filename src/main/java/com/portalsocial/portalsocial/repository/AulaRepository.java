package com.portalsocial.portalsocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portalsocial.portalsocial.domain.model.Aula;

public interface AulaRepository extends JpaRepository<Aula,Long>{
	
	// jpql syntax
//	@Query("SELECT MAX(dataAtualizacao) FROM FormaPagamento")
//	OffsetDateTime getDataUltimaAtualizacao();

}
