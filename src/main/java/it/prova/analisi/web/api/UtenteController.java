package it.prova.analisi.web.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.analisi.dto.UtenteDTO;
import it.prova.analisi.model.Utente;
import it.prova.analisi.security.dto.UtenteInfoJWTResponseDTO;
import it.prova.analisi.service.UtenteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	// testare chi ha accesso
	@GetMapping(value = "/userInfo")
	public ResponseEntity<UtenteInfoJWTResponseDTO> getUserInfo() {
		// se sono qui significa che sono autenticato quindi devo estrarre le info dal
		// contesto
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		List<String> ruoli = utenteLoggato.getRuoli().stream().map(item -> item.getCodice())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new UtenteInfoJWTResponseDTO(utenteLoggato.getNome(), utenteLoggato.getCognome(),
				utenteLoggato.getUsername(), utenteLoggato.getEmail(), ruoli));
	}

	@GetMapping
	public List<UtenteDTO> listUtenti() {
		return utenteService.listAllUtenti().stream().map(utenti -> {
			return UtenteDTO.buildUtenteDTOFromModel(utenti);
		}).collect(Collectors.toList());
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void inserisciNuovo(@Valid @RequestBody UtenteDTO input) {
		Utente utente = input.buildUtenteModel(false);
		utenteService.inserisciNuovo(utente);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(name = "id" , required = true) Long id) {
		utenteService.rimuovi(id);
	}
	
	
	@PutMapping
	public UtenteDTO aggiorna(@Valid @RequestBody UtenteDTO input) {
		Utente aggiornata = input.buildUtenteModel(false);
		aggiornata = utenteService.aggiorna(aggiornata);
		return UtenteDTO.buildUtenteDTOFromModel(aggiornata);
	}


}
