package it.prova.analisi.service;

import java.util.List;

import it.prova.analisi.model.Analisi;

public interface AnalisiService {
	
public List<Analisi> listAll();
	
	public List<Analisi> listMine(String username);
	
	public Analisi findById(Long id, String username);
	
	public Analisi inserisciNuova(Analisi input, String username);
	
	public Analisi aggiorna(Analisi input, String username);
	
	public void delete(Long id, String username);

}
