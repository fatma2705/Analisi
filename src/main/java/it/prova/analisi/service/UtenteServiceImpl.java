package it.prova.analisi.service;

import java.util.HashSet;
import java.util.List;
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

@Service
@Transactional
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository uteteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RuoloRepository ruoloRepository;

	@Override
	public List<Utente> listAllUtenti() {
		return (List<Utente>) uteteRepository.findAll();
	}

	@Override
	public Utente caricaSingoloUtente(Long id) {
		return uteteRepository.findById(id).orElse(null);
	}

	@Override
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return uteteRepository.fingByIdConRuoli(id).orElse(null);
	}

	@Override
	public void aggiorna(Utente utenteInstance) {
		// TODO Auto-generated method stub

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
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		uteteRepository.save(utenteInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Utente> findByExample(Utente example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utente eseguiAccesso(String username, String password) {
		Utente user = uteteRepository.findByUsername(username);
		if(user != null && passwordEncoder.matches(password, user.getPassword()))
			return user;
		return null;
	}

	@Override
	public void changeUserAbilitation(Long utenteInstanceId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Utente findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
