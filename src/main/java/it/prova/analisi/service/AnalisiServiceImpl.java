package it.prova.analisi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.analisi.model.Analisi;
import it.prova.analisi.model.Utente;
import it.prova.analisi.repository.analisi.AnalisiRepository;
import it.prova.analisi.repository.utente.UtenteRepository;
import it.prova.analisi.web.api.exception.ElementNotFoundException;
import it.prova.analisi.web.api.exception.IdNotNullForInsertException;
import it.prova.analisi.web.api.exception.NotSamePazienteException;

@Service
@Transactional
public class AnalisiServiceImpl implements AnalisiService {

	@Autowired
	private AnalisiRepository analisiRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public List<Analisi> listAll() {
		return (List<Analisi>) analisiRepository.findAll();
	}

	@Override
	public List<Analisi> listMine(String username) {
		return analisiRepository.listAllAnalisiOfUtente(username);
	}

	@Override
	public Analisi findById(Long id, String username) {
		Optional<Utente> pazienteOptional = utenteRepository.findByUsername(username);
		Utente paziente = pazienteOptional.get();
		Analisi analisi = analisiRepository.findByIdEager(id);
		if (analisi.getPaziente().isAdmin())
			return analisi;
		else if (analisi.getPaziente().getId() == paziente.getId())
			return analisi;
		else
			throw new NotSamePazienteException("The current Paziente and the Analisi's paziente are not the same");

	}

	@Override
	public Analisi inserisciNuova(Analisi input, String username) {
		Utente paziente = utenteRepository.findByUsername(username).orElse(null);
		if (input.getId() != null) {
			throw new IdNotNullForInsertException("Id must be null for this operation");
		}
		input.setPaziente(paziente);
		return analisiRepository.save(input);
	}

	@Override
	public Analisi aggiorna(Analisi input, String username) {
		if (analisiRepository.findById(input.getId()) == null)
			throw new ElementNotFoundException("Couldn't find analisi with id:" + input.getId());
		if (input.getPaziente() == null) {
			Optional<Utente> pazienteOptional = utenteRepository.findByUsername(username);
			Utente paziente = pazienteOptional.get();
			input.setPaziente(paziente);
		}
		if (!input.getPaziente().getUsername().equals(username))
			throw new NotSamePazienteException("The current Paziente and the Analisi's paziente are not the same");
		input.setPaziente(utenteRepository.findByUsername(username).orElse(null));
		return analisiRepository.save(input);
	}

	@Override
	public void delete(Long id, String username) {
		Analisi analisi = analisiRepository.findByIdEager(id);
		if (analisi.getPaziente().getUsername().equals(username))
			analisiRepository.deleteById(id);
		else
			throw new NotSamePazienteException("The current Paziente and the Analisi's paziente are not the same");

	}

	@Override
	public List<Analisi> findByExample(Analisi example, String username) {

		Optional<Utente> pazienteOptional = utenteRepository.findByUsername(username);
		if (!pazienteOptional.isPresent()) {
			throw new ElementNotFoundException("Username not found: " + username);
		}
		Utente paziente = pazienteOptional.get();

		if (example.getPaziente() == null) {
			example.setPaziente(paziente);
		}

		// Usa la query personalizzata per trovare le analisi
		List<Analisi> analisiList = analisiRepository.findByExample(example.getPaziente().getUsername(), // Passa il
																											// nome
																											// utente
																											// del
																											// paziente
				example.getEsitoPositivo(), example.getTipo(), example.getData());

		// Filtra i risultati basati sul ruolo del paziente o sulla proprietà
		List<Analisi> filteredAnalisi = analisiList.stream()
				.filter(a -> a.getPaziente().isAdmin() || a.getPaziente().getUsername().equals(username))
				.collect(Collectors.toList());
//
//		if (filteredAnalisi.isEmpty()) {
//			throw new NotSamePazienteException("Il paziente corrente e il paziente dell'analisi non corrispondono");
//		}

		return filteredAnalisi;
	}
}
