package com.financeiro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.financeiro.model.DescricaoFinanceira;
import com.financeiro.model.EntidadeFinanceira;
import com.financeiro.repository.DescricaoRepository;
import com.financeiro.repository.FinanceiroRepository;


@Controller
public class FinanceiroController {

	//abaixo instaciamos as entidades e os repository para trabalharmos dentro dessa classe
	private EntidadeFinanceira entidadeFinanceira;
	@Autowired
	private FinanceiroRepository financeiroRepository;
	@Autowired
	private DescricaoRepository descricaoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "**/cadastroprincipal") // esse metodo e o zica de intenderkkk
	public ModelAndView inicio() {// basicamente antes ele era uma pagina html e o metodo retornava uma string,
									// mas com o uso do Thymeleaf
		ModelAndView modelAndView = new ModelAndView("index");// ele tem que retornar um objeto agora
		modelAndView.addObject("financeiroobj", new EntidadeFinanceira());
		return modelAndView;
	}

	/*
	 * metodo salvar somente @RequestMapping(method =
	 * RequestMethod.POST,value="/salvarfinanceiro") public String
	 * salvar(EntidadeFinanceira financeira) {
	 * financeiroRepository.save(financeira); return "index"; }
	 */

	//METODO DE SALVAR
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarfinanceiro") 
	
	// os dois ** faz com que o programa intercepte apenas a url salvarfinanceiro e ignore o que vem antes
	
	
	//if e o Bloco de Validação	
	public ModelAndView salvar(@Valid EntidadeFinanceira financeira, BindingResult bindingResult) {//temos que dizer pro spring que ele precisa validar os dados atraves da @Valid,o objeto bindingResult e para trabalharmos os resultados das validações
		
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("index");
			Iterable<EntidadeFinanceira> entidadeIt = financeiroRepository.findAll();
			modelAndView.addObject("financeiros", entidadeIt);
			modelAndView.addObject("financeiroobj", financeira);// tem que passar o objeto vazio antes
			
			List<String> msg = new ArrayList<String>();//se houver erros seram salvos nessa lista
			for (ObjectError objectError : bindingResult.getAllErrors()) {//varre a lista de erros
				msg.add(objectError.getDefaultMessage());// vem das anotações de validação
			}
			modelAndView.addObject("msg",msg);//coloca as mensagens no objeto para retornar para tela
			return modelAndView;
		}
		
		financeiroRepository.save(financeira);
		
		ModelAndView modelAndView = new ModelAndView("index");

		Iterable<EntidadeFinanceira> entidadeIt = financeiroRepository.findAll();// buscando ja para mostrar na tela
																					// apos salvar
		modelAndView.addObject("financeiroobj", new EntidadeFinanceira());// tem que passar o objeto vazio antes esse objeto esta declarado no meu front end e sem ele o thymeleaf acusa erro
		modelAndView.addObject("financeiros", entidadeIt);// jogando os dados na view

		return modelAndView;

	}
	
	//metodo de salvar os comentarios pra cada mes
	@PostMapping("**/addcomentario/{idfinanceiro}")
	public ModelAndView addcomentario(@Valid DescricaoFinanceira descricaoFinanceira, BindingResult bindingResult, @PathVariable("idfinanceiro") Long idfinanceiro) {
		
		
		if (bindingResult.hasErrors()) {
			
			ModelAndView modelAndView = new ModelAndView("teste/observacao");
			EntidadeFinanceira entidadeFinanceira = financeiroRepository.findById(idfinanceiro).get();
			descricaoFinanceira.setEntidadeFinanceira(entidadeFinanceira);
			modelAndView.addObject("financeiroobj", entidadeFinanceira);// tem que passar o objeto vazio antes
			modelAndView.addObject("observacaoo", descricaoRepository.getDescricao(idfinanceiro));
			
			List<String> msg2 = new ArrayList<String>();//se houver erros seram salvos nessa lista
			for (ObjectError objectError : bindingResult.getAllErrors()) {//varre a lista de erros
				msg2.add(objectError.getDefaultMessage());// vem das anotações de validação
			}
			modelAndView.addObject("msg2",msg2);//coloca as mensagens no objeto para retornar para tela
			return modelAndView;
			
		}
		
		descricaoRepository.save(descricaoFinanceira);	
		EntidadeFinanceira entidadeFinanceira = financeiroRepository.findById(idfinanceiro).get();//quando eu clico no nome do mes cadastrado ele me retorna para a pagina de descrições do mes e para saber qual mes eu estou passando o id do mes como parametro na chamada do metodo e recuperando esse id nessa linha
		descricaoFinanceira.setEntidadeFinanceira(entidadeFinanceira);
		ModelAndView modelAndView = new ModelAndView("teste/observacao");
		modelAndView.addObject("financeiroobj", entidadeFinanceira);// tem que passar o objeto vazio antes
		modelAndView.addObject("observacaoo", descricaoRepository.getDescricao(idfinanceiro));
		return modelAndView;
		
	}
		
//metodo de pesquisa todos os meses ja salvos 
	@RequestMapping(method = RequestMethod.GET, value = "**/listafinanceira")
	public ModelAndView financeiros() {

		ModelAndView andView = new ModelAndView("index");
		andView.addObject("financeiroobj", new EntidadeFinanceira());// tem que passar o objeto vazio antes
		Iterable<EntidadeFinanceira> entidadeIt = financeiroRepository.findAll();
		andView.addObject("financeiros", entidadeIt);
		return andView;
	}

	@GetMapping("**/editarfinanceiro/{idfinanceiro}")
	public ModelAndView editar(@PathVariable("idfinanceiro") Long idFinanceiro) {
		Optional<EntidadeFinanceira> entidadeFinanceira = financeiroRepository.findById(idFinanceiro);// carrega o
																										// objeto do
																										// banco de
																										// dados
		ModelAndView modelAndView = new ModelAndView("index");// prepara o retorno do modelandview
		modelAndView.addObject("financeiroobj", entidadeFinanceira.get());// passa o objeto para a tela
		return modelAndView;
	}

	//metodo de remover mes por id
	@GetMapping("**/removerfinanceiro/{idfinanceiro}")
	public ModelAndView excluir(@PathVariable("idfinanceiro") Long idFinanceiro) {
		financeiroRepository.deleteById(idFinanceiro);
		
		ModelAndView modelAndView = new ModelAndView("index");// prepara o retorno do modelandview
		modelAndView.addObject("financeiros", financeiroRepository.findAll());// passa o objeto requisitado do banco de dados para financeiros
		modelAndView.addObject("financeiroobj", new EntidadeFinanceira());// tem que passar o objeto vazio antes

		return modelAndView;
	}
	
	//metodo de pesquisa por nome e letras
	@PostMapping("**/pesquisarFinanceiro")
	public ModelAndView pesquisar(@RequestParam("nomePesquisa") String nomePesquisa) {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("financeiros", financeiroRepository.findEntidadeFinanceiraByNumero(nomePesquisa));
		modelAndView.addObject("financeiroobj", new EntidadeFinanceira());// tem que passar o objeto vazio antes
		return modelAndView;
		
	}

	@GetMapping("**/observacao/{idfinanceiro}")
	public ModelAndView observacoes(@PathVariable("idfinanceiro") Long idFinanceiro) {
		Optional<EntidadeFinanceira> entidadeFinanceira = financeiroRepository.findById(idFinanceiro);// carrega o
																										// objeto do
																										// banco de
																										// dados
		ModelAndView modelAndView = new ModelAndView("/teste/observacao");// prepara o retorno do modelandview
		modelAndView.addObject("financeiroobj", entidadeFinanceira.get());// passa o objeto para a tela
		modelAndView.addObject("observacaoo", descricaoRepository.getDescricao(idFinanceiro));
		return modelAndView;
	}
	
	
	@GetMapping("**/removerobservacao/{idobservacao}")
	public ModelAndView excluirobservacao(@PathVariable("idobservacao") Long idobservacao) {
		EntidadeFinanceira financeira = descricaoRepository.findById(idobservacao).get().getEntidadeFinanceira();// essa linha estamos recuperando atraves da entidade filha o id da entidade pai amarrada ao dado a ser apagado
		descricaoRepository.deleteById(idobservacao);
		
		ModelAndView modelAndView = new ModelAndView("teste/observacao");// prepara o retorno do modelandview
		modelAndView.addObject("observacaoo", descricaoRepository.getDescricaoMes(financeira.getMes()));// como temos a tabela de retorno de dados de observacao esse codigo pega do banco de dados todos os dados e joga para o front end, se não ouver dados ele retorna um objeto vazio, com essa abordagem do thymeleaf sempre e necessario estarmos retornando esse objeto uma vez que o front end tem esse objeto declarado e espera receber algo.
		modelAndView.addObject("financeiroobj", financeira);// tem que passar o objeto vazio antes

		return modelAndView;
	}
	
}
