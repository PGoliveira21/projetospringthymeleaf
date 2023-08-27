package com.financeiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.model.DescricaoFinanceira;

@Repository
@Transactional
public interface DescricaoRepository extends CrudRepository<DescricaoFinanceira, Long>{

	@Query("select d from DescricaoFinanceira d where d.entidadeFinanceira.id = ?1")
	public List<DescricaoFinanceira> getDescricao(Long idfinanceiro);
	
}
