package com.financeiro.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class DescricaoFinanceira {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String comentario;
	
	private String valor;
	

	@ManyToOne//anotacao de muitos para um
	@JoinColumn(name = "entidadeFinanceira_id", foreignKey = @ForeignKey(name = "entidade_id"))//no curso e usado apenas a anotacao foreignkey mas esta obsoleta e tive que utilizar esse comando
	private EntidadeFinanceira entidadeFinanceira;//preciso instaciar a classe da tabela a ser relacionada 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}

	public EntidadeFinanceira getEntidadeFinanceira() {
		return entidadeFinanceira;
	}

	public void setEntidadeFinanceira(EntidadeFinanceira entidadeFinanceira) {
		this.entidadeFinanceira = entidadeFinanceira;
	}
	
	
	
	
}
