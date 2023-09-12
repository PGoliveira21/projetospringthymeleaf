package com.financeiro.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import javax.validation.constraints.NotNull;
import javax.persistence.CascadeType;
import java.io.Serializable;
import java.util.List;

@Entity
public class EntidadeFinanceira implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
   
   
    @NotEmpty(message = "Mês nao pode ser Vazio")
    private String mes;

    @OneToMany(mappedBy ="entidadeFinanceira", orphanRemoval = true, cascade = CascadeType.ALL)//mapeamento da entidade,um para muitos,(cuidado com o nome do objeto), a parte orphanRemoval = true, cascade = CascadeType.ALL e para deletarmos o mes mesmo se ouver dados para esse mes, o spring executa em cascata os deletes
    private List<DescricaoFinanceira> descricao;//devemos instaciar uma list para trabalharmos com uma lista de dados que pode ser retornada do
    
    
  


	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}


	
	@NotNull(message = "Valor de salario não pode ser nulo")//para implementar essa anotação de validação foi necessario mudar o tipo de double para Double para que o java conseguice tratar, sempre vou criar os campos da tabela usando classes wrapper
    private Double salario;

   
	public void setSalario(Double salario) {
		this.salario = salario;
	}



	
	@NotNull(message = "Valor de gastos não pode ser nulo")
    private Double gastos;
    

    // Construtores, getters e setters (pode ser gerado automaticamente em muitas IDEs).
    
    public List<DescricaoFinanceira> getDescricao() {
		return descricao;
	}

	public void setDescricao(List<DescricaoFinanceira> descricao) {
		this.descricao = descricao;
	}

	public Double getSalario() {
		return salario;
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



	public Double getGastos() {
		return gastos;
	}

	public void setGastos(Double gastos) {
		this.gastos = gastos;
	}

	public EntidadeFinanceira(){
	}
    // Getters e setters para os campos acima...
}
