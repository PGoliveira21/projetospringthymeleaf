package com.financeiro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration// diz que e uma classe de configuração que estamos escrevendo
@EnableWebSecurity//ativa varios metodos de configuração de segurança
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
     @Override //configura as solicitações de acesso por http
    protected void configure(HttpSecurity http) throws Exception {
    	 http.csrf()
 		.disable() // Desativa as configurações padrão de memória.
 		.authorizeRequests() // Pertimir restringir acessos
 		.antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer usuário acessa a pagina inicial
 		.antMatchers(HttpMethod.GET, "/cadastroprincipal").hasAnyRole("ADMIN")// so os usuarios admins podem acessar essa pagina
 		.anyRequest().authenticated()
 		.and().formLogin().permitAll() // permite qualquer usuário
 		.and().logout() // Mapeia URL de Logout e invalida usuário autenticado
 		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
   
    }
     
     @Override // Cria autenticação do usuário com banco de dados ou em memória
 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth.userDetailsService(implementacaoUserDetailsService)
 		.passwordEncoder(new BCryptPasswordEncoder()); 
 		
 		
    	 /*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())//inicializa o objeto BCryptPasswordEncoder que e o responsavel por criptografar e descriptografar a senha
 		.withUser("alex")   TRECHO DE IMPLEMENTACAO EM MEMORIA, NAO E MAIS UTILIZADO POIS NO CODIGO A CIMA ESTAMOS UTILIZANDO O BANCO DE DADOS
 		.password("$2a$10$Xnjw09p8DffXlccIuqEV.OcmmdBGd71QrqmtLrvTAqiZIqa6rtky6")
 		.roles("ADMIN");*/
 	}
 	
     
     @Override // Ignora URL especificas
 	public void configure(WebSecurity web) throws Exception {
 		web.ignoring().antMatchers("/static/**");// nesse caso não possui nada de css pois estou usando direto da internet via cdn
 	}
}
