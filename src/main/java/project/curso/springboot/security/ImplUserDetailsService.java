package project.curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.curso.springboot.domain.Usuario;
import project.curso.springboot.repository.UsuarioRepository;

@Service
@Transactional
public class ImplUserDetailsService implements UserDetailsService {
	
	@Autowired//injeção de dependência
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findUserByLogin(username);
		
		if (usuario == null) {
			//essa mensagem aparece no console caso não encontre o usuário
			throw new UsernameNotFoundException("Usuário não foi encontrado!");
		}
		return new User(
				usuario.getLogin(), 
				usuario.getSenha(), 
				usuario.isEnabled(), 
				true, 
				true, 
				true, 
				usuario.getAuthorities()
				);
	}

}
