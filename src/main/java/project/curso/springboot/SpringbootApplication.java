package project.curso.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = "project.curso.springboot.domain")
@ComponentScan(basePackages = {"project.**"})
@EnableJpaRepositories(basePackages = {"project.curso.springboot.repository"})
@EnableTransactionManagement
@EnableWebMvc
public class SpringbootApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		/*
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String result = bCryptPasswordEncoder.encode("123");
		System.out.println(result);
		*/
		
	}
	
	/**
	 * Método padrão do -> WebMvcConfigurer
	 * Sobrescrevendo o metodo de configuração para direcionar para a página de login personalizada.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		/*mapeando a URL login e a página de login*/
		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
	}

}
