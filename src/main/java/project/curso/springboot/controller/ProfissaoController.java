package project.curso.springboot.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.curso.springboot.domain.Profissao;
import project.curso.springboot.repository.ProfissaoRepository;

@Controller
public class ProfissaoController {

	@Autowired
	private ProfissaoRepository profissaoRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroprofissao")
	public ModelAndView inicio() {
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroprofissao");
		/*carrega a lista de profissões quando a página é iniciada*/
		Iterable<Profissao> profissaoIterable = profissaoRepository.findAll();
		mav.addObject("profissoes", profissaoIterable);
		/*da um new no objeto de profissão*/
		mav.addObject("profissaoObject", new Profissao());
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/saveprofession")
	public ModelAndView save(@Valid Profissao profissao, BindingResult bindingResult) {
		
		profissaoRepository.save(profissao);
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroprofissao");
		Iterable<Profissao> profissaoIterable = profissaoRepository.findAll();
		mav.addObject("profissoes", profissaoIterable);
		mav.addObject("profissaoObject", new Profissao());
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listprofession")
	public ModelAndView listarTodos() {
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroprofissao");
		Iterable<Profissao> profissaoIterable = profissaoRepository.findAll();
		mav.addObject("profissoes", profissaoIterable);
		mav.addObject("profissaoObject", new Profissao());
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/updateprofession/{codigoProfissao}")
	public ModelAndView atualizar(@PathVariable ("codigoProfissao") Long codigoProfissao) {
		
		Optional<Profissao> proOptional = profissaoRepository.findById(codigoProfissao);
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroprofissao");
		mav.addObject("profissaoObject", proOptional.get());
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/deleteprofession/{codigoProfissao}")
	public ModelAndView excluir(@PathVariable ("codigoProfissao") Long codigoProfissao) {
		
		profissaoRepository.deleteById(codigoProfissao);
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroprofissao");
		mav.addObject("profissoes", profissaoRepository.findAll());
		mav.addObject("profissaoObject", new Profissao());
		
		return mav;
	}
}
