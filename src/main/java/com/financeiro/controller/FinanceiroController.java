package com.financeiro.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarfinanceiro") // os dois ** faz com que o programa
																				// intercepte apenas a url
																				// salvarfinanceiro e ignore o que vem
																				// antes
	public ModelAndView salvar(EntidadeFinanceira financeira) {
		ModelAndView andView = new ModelAndView("index");
		andView.addObject("financeiroobj", new EntidadeFinanceira());// tem que passar o objeto vazio antes
		financeiroRepository.save(financeira);

		Iterable<EntidadeFinanceira> entidadeIt = financeiroRepository.findAll();// buscando ja para mostrar na tela
																					// apos salvar
		andView.addObject("financeiros", entidadeIt);// jogando os dados na view

		return andView;

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
	
	//metodo de salvar os comentarios pra cada mes
	@PostMapping("**/addcomentario/{idfinanceiro}")
	public ModelAndView addcomentario(DescricaoFinanceira descricaoFinanceira, @PathVariable("idfinanceiro") Long idfinanceiro) {

		EntidadeFinanceira entidadeFinanceira = financeiroRepository.findById(idfinanceiro).get();
		descricaoFinanceira.setEntidadeFinanceira(entidadeFinanceira);
		descricaoRepository.save(descricaoFinanceira);
		
		
		ModelAndView modelAndView = new ModelAndView("teste/observacao");
		modelAndView.addObject("financeiroobj", entidadeFinanceira);// tem que passar o objeto vazio antes
		modelAndView.addObject("observacaoo", descricaoRepository.getDescricao(idfinanceiro));


		return modelAndView;
		
	}
	
}
