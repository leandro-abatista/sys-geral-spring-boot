package project.curso.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import project.curso.springboot.domain.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	@Query("select p from Pessoa p where p.nome like %?1%")
	List<Pessoa> findPessoaByName(String nome);
	
	@Query("select p from Pessoa p where p.sexo = ?1")
	List<Pessoa> findPessoaBySexo(String sexo);
	
	@Query("select p from Pessoa p where p.nome like %?1% and p.sexo = ?2")
	List<Pessoa> findPessoaByNameSexo(String nome, String sexo);
	
	/**
	 * Método busca pelo nome da pessoa
	 * @param nome
	 * @param pageable
	 * @return
	 */
	default Page<Pessoa> findPessoaByNamePage(String nome, Pageable pageable){
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		
		/*configurando a pesquisa, para pesquisar por partes o nome da pessoa no banco de dados*/
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().
				withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		/*Uni o objeto com o valor e a configuração de consulta*/
		Example<Pessoa> example = Example.of(pessoa, exampleMatcher);
		/*executou no banco*/
		Page<Pessoa> pessoasPage = findAll(example, pageable);
		/*retorna*/
		return pessoasPage;
	}
	
	/**
	 * Método busca pelo nome e o sexo da pessoa
	 * @param nome
	 * @param pageable
	 * @return
	 */
	default Page<Pessoa> findPessoaByNameSexoPage(String nome, String sexo, Pageable pageable) {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		pessoa.setSexo(sexo);

		/*
		 * configurando a pesquisa, para pesquisar por partes o nome da pessoa no banco
		 * de dados
		 */
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().
				withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()).
				withMatcher("sexo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
				
		/* Uni o objeto com o valor e a configuração de consulta */
		Example<Pessoa> example = Example.of(pessoa, exampleMatcher);
		/* executou no banco */
		Page<Pessoa> pessoasPage = findAll(example, pageable);
		/* retorna */
		return pessoasPage;
	}
	
}
