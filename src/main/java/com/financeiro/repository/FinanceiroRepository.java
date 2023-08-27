package com.financeiro.repository;

import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.EntidadeFinanceira;

@Repository
@Transactional
public interface FinanceiroRepository extends CrudRepository<EntidadeFinanceira, Long> {
    // Você pode adicionar consultas personalizadas aqui, se necessário.
	
	@Query("SELECT p FROM EntidadeFinanceira p WHERE LOWER(p.mes) LIKE LOWER(concat('%', ?1, '%'))")
	List<EntidadeFinanceira> findEntidadeFinanceiraByNumero(String mes);
}

