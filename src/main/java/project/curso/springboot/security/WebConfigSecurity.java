package project.curso.springboot.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity//habilita os recursos de segurança em nossa aplicação
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired//injeção de dependência
	private ImplUserDetailsService implUserDetailsService;

	@Override//Configura as solicitações de acesso via http.
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()//Desativa as configurções padrão de memória do spring.
		.authorizeRequests() //permite restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll()//qualquer usuário acessa a página inicial
		.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN")//SÓ TEM ACESSO A PÁGINA cadastropessoa, O USUÁRIO ADMIN
		.antMatchers(HttpMethod.GET, "/cadastroramoatividade").hasAnyRole("ADMIN")//SÓ TEM ACESSO A PÁGINA cadastropessoa, O USUÁRIO ADMIN
		.antMatchers(HttpMethod.GET, "/cadastrofornecedor").hasAnyRole("ADMIN")//SÓ TEM ACESSO A PÁGINA cadastropessoa, O USUÁRIO ADMIN
		.anyRequest().authenticated()
		.and().formLogin()//permite qualquer usuario
		.loginPage("/login").permitAll()//pagina de login
		.defaultSuccessUrl("/cadastropessoa")//depois que realizar o login e tiver acesso, aí vai para a tela de cadastro
		.failureUrl("/login?error=true")//se falhar, volta para a tela de login novamente
		.and().logout().logoutSuccessUrl("/login")//mapeia URL para sair do sistema e invalida o usuario autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override//Cria a autenticação de usuário com o banco de dados, ou em memória.
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/**/
		auth.userDetailsService(implUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		
		/*
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.withUser("maria")
		.password("maria123")
		.roles("ADMIN");
		*/
	}
	
	@Override//Ignora URL's específicas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
	}
}
