package com.financeiro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Entity
public class EntidadeFinanceira implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String mes;

    @OneToMany(mappedBy ="entidadeFinanceira")//mapeamento da entidade, 
    private List<DescricaoFinanceira> descricao;
    
    
    public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}


	@Column(nullable = false)
    private double salario;

    @Column(nullable = false)
    private double gastos;
    

    // Construtores, getters e setters (pode ser gerado automaticamente em muitas IDEs).
    
    public List<DescricaoFinanceira> getDescricao() {
		return descricao;
	}

	public void setDescricao(List<DescricaoFinanceira> descricao) {
		this.descricao = descricao;
	}

	// Exemplo de construtor:
    public EntidadeFinanceira(double salario, double gastos) {
        this.salario = salario;
        this.gastos = gastos;
       
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public double getGastos() {
		return gastos;
	}

	public void setGastos(double gastos) {
		this.gastos = gastos;
	}


	public EntidadeFinanceira(){
	}
    // Getters e setters para os campos acima...
}
