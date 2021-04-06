package project.curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.curso.springboot.domain.RamoAtividade;
import project.curso.springboot.repository.RamoAtividadeRepository;

@Controller
public class RamoAtividadeController {

	@Autowired
	private RamoAtividadeRepository ramoAtividadeRepository;
	
	/**
	 * Método de inicialização da tela de cadastro de pessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroramoatividade")
	public ModelAndView inicio() {
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroramoatividade");
		/*carrega a tabela de pessoas cadastradas assim que entra na página*/
		Iterable<RamoAtividade> ramoDeAtividadeIterable = ramoAtividadeRepository.findAll();
		mav.addObject("ramosDeAtividades", ramoDeAtividadeIterable);
		mav.addObject("ramoAtividadeObject", new RamoAtividade());
		
		return mav;
	}
	
	/**
	 * Método para salvar uma pessoa
	 * @param pessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "**/saveramoatividade")
	public ModelAndView salvar(@Valid RamoAtividade ramoAtividade, BindingResult bindingResult) {
		
		/*se tiver erro, volta para a tela e deixa os dados em tela para corrigir*/
		if (bindingResult.hasErrors()) {
			
			ModelAndView mav = new ModelAndView("cadastro/cadastroramoatividade");
			Iterable<RamoAtividade> ramoDeAtividadeIterable = ramoAtividadeRepository.findAll();
			mav.addObject("ramosDeAtividades", ramoDeAtividadeIterable);
			mav.addObject("ramoAtividadeObject", new RamoAtividade());
			
			//aqui valida os erros das anotações e adiciona na lista de erros para serem mostrados ao usuário
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // -> vem das anotações
			}
			
			mav.addObject("msg", msg);
			return mav;
		}
		
		
		ramoAtividadeRepository.save(ramoAtividade);	
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroramoatividade");
		Iterable<RamoAtividade> ramoDeAtividadeIterable = ramoAtividadeRepository.findAll();
		mav.addObject("ramosDeAtividades", ramoDeAtividadeIterable);
		mav.addObject("ramoAtividadeObject", new RamoAtividade());
		
		return mav;
		//return "cadastro/cadastropessoa";
	}
	
	/**
	 * Método para listar todas 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/listramosdeatividades")
	public ModelAndView listarTodos(){
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroramoatividade");
		Iterable<RamoAtividade> ramoDeAtividadeIterable = ramoAtividadeRepository.findAll();
		mav.addObject("ramosDeAtividades", ramoDeAtividadeIterable);
		mav.addObject("ramoAtividadeObject", new RamoAtividade());
		
		return mav;
	}
	
	/**
	 * Método para atualizar 
	 * @param codigopessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/updateramodeatividade/{codigoramoatividade}")
	public ModelAndView atualizar(@PathVariable ("codigoramoatividade") Long codigoramoatividade) {
		
		Optional<RamoAtividade> ramoDeAtividade = ramoAtividadeRepository.findById(codigoramoatividade);
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroramoatividade");
		mav.addObject("ramoAtividadeObject", ramoDeAtividade.get());
		
		return mav;
	}
	
	/**
	 * Método para excluir 
	 * @param codigopessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/deleteramodeatividade/{codigoramoatividade}")
	public ModelAndView excluir(@PathVariable ("codigoramoatividade") Long codigoramoatividade) {
		
		ramoAtividadeRepository.deleteById(codigoramoatividade);
		
		ModelAndView mav = new ModelAndView("cadastro/cadastroramoatividade");
		mav.addObject("ramosDeAtividades", ramoAtividadeRepository.findAll());
		mav.addObject("ramoAtividadeObject", new RamoAtividade());//retorna um object vazio
		
		return mav;
	}
}
