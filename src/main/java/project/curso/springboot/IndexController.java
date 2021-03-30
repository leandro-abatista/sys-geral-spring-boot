package project.curso.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Classe para testar se o projeto está funcionando
 * @author Leandro
 *
 */
@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
