package project.curso.springboot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import project.curso.springboot.domain.Usuario;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	/*A query busca o login no bd, de acordo com o parâmetro passado no método na qual é o login e na posição 1*/
	@Query("select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);
}
