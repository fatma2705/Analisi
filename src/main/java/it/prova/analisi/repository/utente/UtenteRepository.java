package it.prova.analisi.repository.utente;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.analisi.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {

	@EntityGraph(attributePaths = { "ruoli" })
	Optional<Utente> findByUsername(String username);

	@Query("from Utente u left join fetch u.ruoli where u.id = ?1")
	Optional<Utente> fingByIdConRuoli(Long id);

	@EntityGraph(attributePaths = { "ruoli" })
	Utente findByUsernameAndPasswordAndAttivo(String username, String password, Boolean attivo);

}
