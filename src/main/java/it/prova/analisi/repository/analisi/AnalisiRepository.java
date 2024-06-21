package it.prova.analisi.repository.analisi;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.analisi.model.Analisi;

public interface AnalisiRepository extends CrudRepository<Analisi, Long> ,  QueryByExampleExecutor<Analisi>{

	@Query(" select a from Analisi a left join fetch a.paziente u where u.username=:username")
	public List<Analisi> listAllAnalisiOfUtente(String username);

	@Query(" select a from Analisi a left join fetch a.paziente u where a.id=:id")
	public Analisi findByIdEager(Long id);

}
