package it.prova.analisi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.analisi.model.Ruolo;
import it.prova.analisi.model.Utente;
import it.prova.analisi.repository.ruolo.RuoloRepository;
import it.prova.analisi.repository.utente.UtenteRepository;
import it.prova.analisi.web.api.exception.IdNotNullForInsertException;
import it.prova.analisi.web.api.exception.PasswordMismatchException;

@Service
@Transactional
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RuoloRepository ruoloRepository;

	@Override
	public List<Utente> listAllUtenti() {
		return (List<Utente>) utenteRepository.findAll();
	}

	@Override
	public Utente caricaSingoloUtente(Long id) {
		return utenteRepository.findById(id).orElse(null);
	}

	@Override
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return utenteRepository.fingByIdConRuoli(id).orElse(null);
	}

	@Override
	public void aggiorna(Utente utenteInstance) {
		Utente utenteReloaded = utenteRepository.findById(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Utente non trovato");
		utenteReloaded.setNome(utenteInstance.getNome());
		utenteReloaded.setCognome(utenteInstance.getCognome());
		utenteReloaded.setCodiceFiscale(utenteInstance.getCodiceFiscale());
		utenteReloaded.setEmail(utenteInstance.getEmail());
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		utenteRepository.save(utenteReloaded);
	}

	@Override
	public void inserisciNuovo(Utente utenteInstance) {
		if (utenteInstance.getId() != null) {
			throw new IdNotNullForInsertException("Id must be null for insert operation");
		}
		Set<Ruolo> ruoli = new HashSet<Ruolo>();
		ruoli.add(ruoloRepository.findByDescrizioneAndCodice("Classic User", Ruolo.ROLE_CLASSIC_USER));
		utenteInstance.setRuoli(ruoli);
		utenteInstance.setAttivo(true);
		if (!(utenteInstance.getPassword().equals(utenteInstance.getConfermaPassword()))){
			throw new PasswordMismatchException("Passwords do not match. Please ensure both password fields are identical");
		}
		utenteInstance.setConfermaPassword(passwordEncoder.encode(utenteInstance.getConfermaPassword()));
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteRepository.save(utenteInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		utenteRepository.deleteById(idToRemove);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return utenteRepository.findByUsernameAndPasswordAndAttivo(username, password, true);
	}

		@Override
		public Utente eseguiAccesso(String username, String password) {
			Optional<Utente> optionalUser = utenteRepository.findByUsername(username);
			Utente user = optionalUser.get();
			if (user != null && passwordEncoder.matches(password, user.getPassword()))
				return user;
			return null;
		}
	
		@Override
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Utente non trovato");
		utenteInstance.setAttivo(false);
	}

	@Override
	public Utente findByUsername(String username) {
		return utenteRepository.findByUsername(username).orElse(null);
	}

}
