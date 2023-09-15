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
	
	@Query("SELECT d FROM DescricaoFinanceira d WHERE d.entidadeFinanceira.mes = ?1")//esse metodo e usado na dela de dados dos gastos do mes para retornar so os gastos referentes ao mes em edição
	public List<DescricaoFinanceira> getDescricaoMes(String mes);

	
}
