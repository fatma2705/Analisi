package it.prova.analisi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.analisi.model.Ruolo;
import it.prova.analisi.repository.ruolo.RuoloRepository;
import it.prova.analisi.web.api.exception.IdNotNullForInsertException;

@Service
@Transactional
public class RuoloServiceImpl implements RuoloService{
	
	@Autowired
	private RuoloRepository ruoloRepository;

	@Override
	public List<Ruolo> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ruolo caricaSingoloElemento(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggiorna(Ruolo ruoloInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inserisciNuovo(Ruolo ruoloInstance) {
		if (ruoloInstance.getId() != null) {
			throw new IdNotNullForInsertException("Id must be null for this operation");
		}
		ruoloRepository.save(ruoloInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) {
		return ruoloRepository.findByDescrizioneAndCodice(descrizione, codice) ;
	}

}
