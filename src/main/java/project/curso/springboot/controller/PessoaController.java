package project.curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import project.curso.springboot.domain.Pessoa;
import project.curso.springboot.domain.Telefone;
import project.curso.springboot.repository.PessoaRepository;
import project.curso.springboot.repository.TelefoneRepository;



/**
 * Classe de controller pessoa
 * @author Leandro
 *
 */
@Controller
public class PessoaController {
	
	@Autowired//pode ser resources também (Tanto autowired, como resources, são injeções de dependência)
	private PessoaRepository pessoaRepository;
	
	@Autowired//pode ser resources também (Tanto autowired, como resources, são injeções de dependência)
	private TelefoneRepository telefoneRepository;
	
	@Autowired//pode ser resources também (Tanto autowired, como resources, são injeções de dependência)
	private ReportUtil reportUtil;
	
	/**
	 * Método de inicialização da tela de cadastro de pessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
		/*carrega a tabela de pessoas cadastradas assim que entra na página*/
		Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
		mav.addObject("pessoas", pessoaIterable);
		
		mav.addObject("pessoaObject", new Pessoa());
		return mav;
	}
	
	/**
	 * Método para salvar uma pessoa
	 * @param pessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		/*pegando os telefones associados a pessoa, pelo codigo da pessoa*/
		pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getCodigo()));
		
		//se tiver erro, volta para a tela e deixa os dados em tela para corrigir
		if (bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
			mav.addObject("pessoas", pessoaIterable);
			mav.addObject("pessoaObject", pessoa);
			//aqui valida os erros das anotações e adiciona na lista de erros para serem mostrados ao usuário
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // -> vem das anotações
			}
			
			mav.addObject("msg", msg);
			return mav;
		}
		
		pessoaRepository.save(pessoa);
		
		ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
		mav.addObject("pessoas", pessoaIterable);
		mav.addObject("pessoaObject", new Pessoa());
		
		return mav;
		//return "cadastro/cadastropessoa";
	}
	
	/**
	 * Método para listar todas as pessoas cadastradas
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/listperson")
	public ModelAndView listarTodos(){
		ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
		mav.addObject("pessoas", pessoaIterable);
		mav.addObject("pessoaObject", new Pessoa());
		return mav;
	}
	
	/**
	 * Método para atualizar uma pessoa
	 * @param codigopessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/atualizarpessoa/{codigopessoa}")
	public ModelAndView atualizar(@PathVariable ("codigopessoa") Long codigopessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigopessoa);
		ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
		mav.addObject("pessoaObject", pessoa.get());
		return mav;
	}
	
	/**
	 * Método para excluir uma pessoa
	 * @param codigopessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/excluirpessoa/{codigopessoa}")
	public ModelAndView excluir(@PathVariable ("codigopessoa") Long codigopessoa) {
		pessoaRepository.deleteById(codigopessoa);
		ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
		mav.addObject("pessoas", pessoaRepository.findAll());
		mav.addObject("pessoaObject", new Pessoa());//retorna um object vazio
		return mav;
	}
	
	/**
	 * Método buscar por nome da pessoa
	 * @param nomePessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "**/pesquisarpessoa")
	public ModelAndView pesquisar(
			@RequestParam ("nomePessoa") String nomePessoa,
			@RequestParam ("sexoPessoa") String sexoPessoa
			) {
		
		List<Pessoa> pessoas = new ArrayList<>();
		
		if (nomePessoa != null && !nomePessoa.isEmpty()) {
			
			pessoas = pessoaRepository.findPessoaByName(nomePessoa);
			
		} else if (sexoPessoa != null && !sexoPessoa.isEmpty()) {
			
			pessoas = pessoaRepository.findPessoaByNameSexo(nomePessoa, sexoPessoa);
		}
		
		ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
		mav.addObject("pessoas", pessoas);//o codigo abaixo foi substituído pela lista de pessoas
		//mav.addObject("pessoas", pessoaRepository.findPessoaByName(nomePessoa));
		mav.addObject("pessoaObject", new Pessoa());//retorna um object vazio
		return mav;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "**/pesquisarpessoa")
	public void imprimePdf(
			@RequestParam ("nomePessoa") String nomePessoa,
			@RequestParam ("sexoPessoa") String sexoPessoa,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<Pessoa> pessoas = new ArrayList<>();
		
		if (sexoPessoa != null && !sexoPessoa.isEmpty() && nomePessoa != null && !nomePessoa.isEmpty()) {/*busca por nome e sexo*/
			
			pessoas = pessoaRepository.findPessoaByNameSexo(nomePessoa, sexoPessoa);
			
		} else if (nomePessoa != null && !nomePessoa.isEmpty()) {/*busca por nome*/
			
			pessoas = pessoaRepository.findPessoaByName(nomePessoa);
			
		} else if (sexoPessoa != null && !sexoPessoa.isEmpty()) {
			
			pessoas = pessoaRepository.findPessoaBySexo(sexoPessoa);
			
		} else {
			Iterable<Pessoa> iterable = pessoaRepository.findAll();/*busca todos*/
			/*varrenndo o iterable com for*/
			for (Pessoa pessoa : iterable) {
				pessoas.add(pessoa);
			}
		}
		
		/*Chama o serviço que faz a geração do relatório | rel_pessoas -> arquivo jasper*/
		byte[] pdf = reportUtil.gerarRelatorio(pessoas, "rel_pessoas", request.getServletContext());
		/*Tamanho da resosta do navegador*/
		response.setContentLength(pdf.length);//navgeador saber o tamanho da resposta
		/*Definir na resposta o tipo de arquivo */
		response.setContentType("application/octet-stream");
		/*Definir o cabeçalho da resposta*/
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment: filename=\"%s\"", "relatorio.pdf");
		response.setHeader(headerKey, headerValue);
		
		/*Finaliza a resposta para o navegador*/
		response.getOutputStream().write(pdf);
		
	}
	
	/**
	 * Método para mostrar dados completos da pessoa em tela
	 * @param codigopessoa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/fones/{codigopessoa}")
	public ModelAndView telefones(@PathVariable ("codigopessoa") Long codigopessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigopessoa);
		ModelAndView mav = new ModelAndView("cadastro/telefones");
		mav.addObject("pessoaObject", pessoa.get());
		mav.addObject("telefones", telefoneRepository.getTelefones(codigopessoa));
		return mav;
	}
	
	/**
	 * Método para adicionar telefone a pessoa através do codigo da pessoa
	 * @param telefone
	 * @param pessoa_codigo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "**/addFonePerson/{pessoa_codigo}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable ("pessoa_codigo") Long pessoa_codigo) {
		//carrega uma pessoa pelo codigo
		Pessoa pessoa = pessoaRepository.findById(pessoa_codigo).get();
		
		if (telefone != null
				&& telefone.getTipo().isEmpty() && telefone.getNumero().isEmpty() || telefone.getNumero() == null) {

			ModelAndView mav = new ModelAndView("cadastro/telefones");// a tela que vai retornar após encontrar o erro
			mav.addObject("pessoaObject", pessoa);// retorna o objeto pessoa
			mav.addObject("telefones", telefoneRepository.getTelefones(pessoa_codigo));// retorna a lista de telefones

			List<String> msg = new ArrayList<>();// validação de mensagem
			if (telefone.getNumero().isEmpty()) {
				msg.add("Número de telefone deve ser informado!");
			}

			if (telefone.getTipo().isEmpty()) {
				msg.add("Tipo de telefone deve ser informado!");
			}

			mav.addObject("msg", msg);
			return mav;
		}
		
		//depois seta o telefone da pessoa
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		ModelAndView mav = new ModelAndView("cadastro/telefones");
		mav.addObject("pessoaObject", pessoa);
		mav.addObject("telefones", telefoneRepository.getTelefones(pessoa_codigo));
		return mav;
	}
	
	/**
	 * Método para remover os telefones
	 * @param codigoTelefone
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/deleteFone/{codigotelefone}")
	public ModelAndView excluirTelefone(@PathVariable ("codigotelefone") Long codigoTelefone) {
		Pessoa pessoa = telefoneRepository.findById(codigoTelefone).get().getPessoa();
		telefoneRepository.deleteById(codigoTelefone);
		ModelAndView mav = new ModelAndView("cadastro/telefones");
		mav.addObject("pessoaObject", pessoa);
		mav.addObject("telefones", telefoneRepository.getTelefones(pessoa.getCodigo()));
		return mav;
	}
	
}
