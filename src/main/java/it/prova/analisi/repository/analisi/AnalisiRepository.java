package it.prova.analisi.repository.analisi;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.analisi.model.Analisi;
import it.prova.analisi.model.TipoAnalisi;

public interface AnalisiRepository extends CrudRepository<Analisi, Long> ,  QueryByExampleExecutor<Analisi>, JpaRepository<Analisi, Long>{

	@Query(" select a from Analisi a left join fetch a.paziente u where u.username=:username")
	public List<Analisi> listAllAnalisiOfUtente(String username);

	@Query(" select a from Analisi a left join fetch a.paziente u where a.id=:id")
	public Analisi findByIdEager(Long id);
	

	    @Query("SELECT a FROM Analisi a WHERE " +
	           "(:username IS NULL OR a.paziente.username = :username) " +
	           "AND (:esitoPositivo IS NULL OR a.esitoPositivo = :esitoPositivo) " +
	           "AND (:tipo IS NULL OR a.tipo = :tipo) " +
	           "AND (:data IS NULL OR a.data = :data)")
	    List<Analisi> findByExample(@Param("username") String username,
	                                @Param("esitoPositivo") Boolean esitoPositivo,
	                                @Param("tipo") TipoAnalisi tipo,
	                                @Param("data") LocalDate data);

	}




