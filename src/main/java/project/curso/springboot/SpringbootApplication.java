package project.curso.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "curso.springboot.domain")
@ComponentScan(basePackages = {"curso.**"})
@EnableJpaRepositories(basePackages = {"curso.springboot.repository"})
@EnableTransactionManagement
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		/*
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String result = bCryptPasswordEncoder.encode("123");
		System.out.println(result);
		*/
	}

}
