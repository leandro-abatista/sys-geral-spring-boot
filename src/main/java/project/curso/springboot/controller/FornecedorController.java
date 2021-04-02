package project.curso.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.curso.springboot.repository.FornecedorRepository;

@Controller
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastrofornecedor")
	public ModelAndView inicio() {
		ModelAndView mav = new ModelAndView("cadastro/cadastrofornecedor");
		
		return mav;
	}
}
