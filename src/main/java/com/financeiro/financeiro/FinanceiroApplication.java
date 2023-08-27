package com.financeiro.financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages="com.financeiro.model")//nunca esquecer dessa declaração
@EnableJpaRepositories(basePackages = {"com.financeiro.repository"})//nunca esquecer dessa declaração
@ComponentScan(basePackages = {"com.financeiro.*"})//nunca esquecer dessa declaração
@EnableTransactionManagement//nunca esquecer dessa declaração
@SpringBootApplication
public class FinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApplication.class, args);
	}

}
