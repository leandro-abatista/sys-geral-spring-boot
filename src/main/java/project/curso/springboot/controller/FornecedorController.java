package project.curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import project.curso.springboot.domain.Fornecedor;
import project.curso.springboot.repository.FornecedorRepository;

@Controller
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	/**
	 * Método de início da página
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/cadastrofornecedor")
	public ModelAndView inicio() {
		ModelAndView mav = new ModelAndView("cadastro/cadastrofornecedor");
		/*carrega a tabela de pessoas cadastradas assim que entra na página*/
		Iterable<Fornecedor> fornecedorIterable = fornecedorRepository.findAll();
		mav.addObject("fornecedores", fornecedorIterable);
		mav.addObject("fornecedorObject", new Fornecedor());
		
		return mav;
	}
	
	/**
	 * Método para salvar um fornecedor
	 * @param fornecedor
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "**/saveprovider")
	public ModelAndView salvar(@Valid Fornecedor fornecedor, BindingResult bindingResult) {
		
		fornecedorRepository.save(fornecedor);
		
		/**/
		ModelAndView mav = new ModelAndView("cadastro/cadastrofornecedor");
		Iterable<Fornecedor> fornecedorIterable = fornecedorRepository.findAll();
		mav.addObject("fornecedores", fornecedorIterable);
		mav.addObject("fornecedorObject", new Fornecedor());
		
		return mav;
	}
	
	/**
	 * Método para listar todos os regsitros de fornecedores
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/listprovider")
	public ModelAndView listarTodos() {
		
		ModelAndView mav = new ModelAndView("cadastro/cadastrofornecedor");
		Iterable<Fornecedor> fornecedorIterable = fornecedorRepository.findAll();
		mav.addObject("fornecedores", fornecedorIterable);
		mav.addObject("fornecedorObject", new Fornecedor());
		
		return mav;
	}
	
	/**
	 * Método para atualizar os dados do fornecedor
	 * @param codigoFornecedor
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/updateprovider/{codigoFornecedor}")
	public ModelAndView atualizar(@PathVariable ("codigoFornecedor") Long codigoFornecedor) {
		
		Optional<Fornecedor> fornOptional = fornecedorRepository.findById(codigoFornecedor);
		ModelAndView mav = new ModelAndView("cadastro/cadastrofornecedor");
		mav.addObject("fornecedorObject", fornOptional.get());
		
		return mav;
	}
	
	/**
	 * Método para excluir um registro de fornecedor
	 * @param codigoFornecedor
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/deleteprovider/{codigoFornecedor}")
	public ModelAndView delete(@PathVariable ("codigoFornecedor") Long codigoFornecedor) {
		
		fornecedorRepository.deleteById(codigoFornecedor);
		ModelAndView mav = new ModelAndView("cadastro/cadastrofornecedor");
		mav.addObject("fornecedores", fornecedorRepository.findAll());
		mav.addObject("fornecedorObject", new Fornecedor());
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/searchprovider")
	public ModelAndView pesquisar(@RequestParam ("codigoFornecedor") Long codigoFornecedor,
			@RequestParam ("pesquisarPorRazaoSocial") String pesquisarPorRazaoSocial) {
		
		List<Fornecedor> fornecedors = new ArrayList<>();
		
		if (codigoFornecedor != null) {
			
			fornecedors = fornecedorRepository.findProvidersByCodigo(codigoFornecedor);
			
		} else if (pesquisarPorRazaoSocial != null && !pesquisarPorRazaoSocial.isEmpty()) {
			
			fornecedors = fornecedorRepository.findProvidersByRazaoSocial(codigoFornecedor, pesquisarPorRazaoSocial);
		}
		
		ModelAndView mav = new ModelAndView("cadastro/cadastrofornecedor");
		mav.addObject("fornecedores", fornecedors);
		mav.addObject("fornecedorObject", new Fornecedor());
		
		return mav;
	}
	
}
