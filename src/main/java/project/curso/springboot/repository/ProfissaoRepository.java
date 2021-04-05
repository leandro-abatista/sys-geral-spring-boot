package project.curso.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import project.curso.springboot.domain.Profissao;

@Repository
@Transactional
public interface ProfissaoRepository extends CrudRepository<Profissao, Long>{

}
