package it.prova.analisi;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.analisi.model.Ruolo;
import it.prova.analisi.model.Utente;
import it.prova.analisi.service.RuoloService;
import it.prova.analisi.service.UtenteService;

@SpringBootApplication
public class AnalisiApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	
	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(AnalisiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", Ruolo.ROLE_CLASSIC_USER));
		}
		Ruolo ruolo = ruoloServiceInstance.caricaSingoloElemento(1L);
		Utente admin = utenteServiceInstance.findByUsername("admin");
		if (admin != null && admin.getRuoli().contains(ruolo)) {
			System.out.println("Admin admin già impostato");
		}else {
			Set<Ruolo> ruoli =  new HashSet<>();
			ruoli.add(ruolo);
			Utente setAdmin = new Utente("admin","admin","admin","admin27@gmail.com","Admin","Admin","ASDFGGCX",true,ruoli);
			utenteServiceInstance.inserisciNuovo(setAdmin);
			System.out.println("admin  settato");
		}
	}
}
