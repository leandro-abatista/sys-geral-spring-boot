package project.curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import project.curso.springboot.domain.Fornecedor;

@Repository
@Transactional
public interface FornecedorRepository extends CrudRepository<Fornecedor, Long>{

	@Query("select f from Fornecedor f where f.codigo = ?1")
	List<Fornecedor> findProvidersByCodigo(Long codigo);
	
	@Query("select f from Fornecedor f where f.codigo = ?1 and f.razaoSocial like %?2%")
	List<Fornecedor> findProvidersByRazaoSocial(Long codigo, String razaoSocial);
}
