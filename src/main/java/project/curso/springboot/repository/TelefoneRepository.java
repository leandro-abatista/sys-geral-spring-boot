package project.curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import project.curso.springboot.domain.Telefone;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long>{

	@Query("select t from Telefone t where t.pessoa.codigo = ?1")
	public List<Telefone> getTelefones(Long pessoa_codigo);
}
